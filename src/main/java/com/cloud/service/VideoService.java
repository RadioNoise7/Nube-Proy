package com.cloud.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import com.cloud.exception.NotFoundException;
import com.cloud.model.User;
import com.cloud.model.Video;
import com.cloud.model.request.VideoRequest;
import com.cloud.repository.AccountRepository;
import com.cloud.repository.VideoRepository;
import com.cloud.service.utility.FileManagerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VideoService {

  @Autowired
  private VideoRepository videoRepository;

  @Autowired
  private AccountRepository cuentaRepository;

  @Autowired
  private AuthService authService;

  private static final long KByte = 1024;
  private static final long MByte = 1024*1024;

  public List<Video> getAllVideos() {
    List<Video> videos = new LinkedList<>();
    videoRepository.findAll().iterator().forEachRemaining(videos::add);
    return videos;
  }

  public List<Video> getVideosByUserId(Integer userId) throws NotFoundException {
    List<Video> videoList = new LinkedList<>();
    if (!existUser(userId)) {
      throw new NotFoundException("No se encontró la cuenta con id " + userId);
    }

    videoList = videoRepository.findByUserId(userId);
    return videoList;
  }

  /*
   * public List<Video> getVideosByProviderId(Integer providerId) { List<Video>
   * videos = new LinkedList<>();
   * 
   * videos = videoRepository.findByProviderId(providerId); return videos; }
   */

  /*---------------------------------------------Nivel usuario común------------------------------------*/
  public List<Video> getUserVideos() {
    User authenticatedUser = authService.getAuthUser();
    List<Video> foundVideos = videoRepository.findByUserId(authenticatedUser.getId());
    return foundVideos;
  }

  @Transactional
  public Video crearVideo(String videoJson, MultipartFile file) throws IOException, NotFoundException {
    ObjectMapper om = new ObjectMapper();
    VideoRequest request = om.readValue(videoJson, VideoRequest.class);

    User authenticatedUser = authService.getAuthUser();
    Video video = new Video();
    FileManagerService fileMS = new FileManagerService();

    video.setUser(authenticatedUser);
    video.setTitle(request.getTitle());
    video.setDescription(request.getDescription());
    video.setFileUrl(fileMS.saveFile(file, authenticatedUser.getId(), FileManagerService.VIDEOS_PATH));
    video = videoRepository.save(video);

    return video;
  }

  public UrlResource getVideoResource(Integer id) throws MalformedURLException {
    Optional<Video> videoFound = videoRepository.findById(id);
    if (!videoFound.isPresent()) throw new NotFoundException("El video con id " + id + " no existe");
    Video video = videoFound.get();
    String ubicacion = video.getFileUrl();
    return new UrlResource("file:" + ubicacion);
  }

  public ResourceRegion getVideoResourceInParts(UrlResource urlResource, HttpHeaders httpHeaders) 
    throws MalformedURLException {
      long videoLength = 0;
      try {videoLength = urlResource.contentLength();}
      catch(IOException ex){System.out.println("Ocurrió un error al obtener el recurso");}
      HttpRange httpRange = httpHeaders.getRange().size() != 0 ? httpHeaders.getRange().get(0) : null;
  
      if(httpRange == null)  {
          long step = Math.min(KByte, videoLength); // los primero 1024 Bytes
          return new ResourceRegion(urlResource, 0, step); // 0 + paso 
      }
  
      long start = httpRange.getRangeStart(videoLength);
      long end = httpRange.getRangeEnd(videoLength);
      long step = Math.min(MByte, end - start + 1); // limitar el maximo a 1 MB
      return new ResourceRegion(urlResource, start, step); // inicio + paso
    }

  @Transactional
  public Video updateVideo(Integer id, VideoRequest request) throws NoSuchElementException {
    Optional<Video> videoFound = videoRepository.findById(id);
    if (!videoFound.isPresent()) throw new NotFoundException("El video con id " + id + " no existe");
    Video video = videoFound.get();
    video.setTitle(request.getTitle());
    video.setDescription(request.getDescription());
    videoRepository.save(video);
    return video;
  }

  @Transactional
  public void deleteVideo(Integer id) throws NoSuchElementException {
    Optional<Video> videoFound = videoRepository.findById(id);
    if (!videoFound.isPresent()) throw new NotFoundException("El video con id " + id + " no existe");
    Video video = videoFound.get();
    videoRepository.delete(video);
    FileManagerService fileMS = new FileManagerService();
    try{fileMS.deleteFile(video.getFileUrl());}
    catch(IOException ex){System.out.println("Error al eliminar el archivo del directorio");}
  }

  private Boolean existUser(Integer userId) {
    return cuentaRepository.findById(userId).isPresent();
  }

  /*
   * private Boolean existProvider(Integer proveedorId) { return
   * proveedorRepository.findById(proveedorId).isPresent(); }
   */
}