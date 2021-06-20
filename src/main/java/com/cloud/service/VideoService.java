package com.cloud.service;

import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import com.cloud.exception.NotFoundException;
import com.cloud.model.Provider;
import com.cloud.model.User;
import com.cloud.model.Video;

import com.cloud.model.Request.VideoRequest;
import com.cloud.repository.AccountRepository;
import com.cloud.repository.ProviderRepository;
import com.cloud.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VideoService {

  @Autowired
  private VideoRepository videoRepository;

  @Autowired
  private AccountRepository cuentaRepository;

  @Autowired
  private ProviderRepository proveedorRepository;

  public List<Video> getAllVideos() {
    List<Video> videos = new LinkedList<>();
    videoRepository.findAll().iterator().forEachRemaining(videos::add);
    return videos;
  }

  public List<Video> getVideosByUserId(Integer userId) throws NotFoundException{
    List<Video> videoList = new LinkedList<>();
    if(!existUser(userId)) {
      throw new NotFoundException("No se encontró la cuenta con id " +userId);
    }

    videoList = videoRepository.findByUserId(userId);
    return videoList;
  }

  /*public List<Video> getVideosByProviderId(Integer providerId) {
    List<Video> videos = new LinkedList<>();

    videos = videoRepository.findByProviderId(providerId);
    return videos;
  }*/

  /*---------------------------------------------Nivel usuario común------------------------------------*/
  public List<Video> getUserVideos() {
    User authenticatedUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    List<Video> foundVideos = videoRepository.findByUserId(authenticatedUser.getId());
    return foundVideos;
  }

  @Transactional
  public Video crearVideo(String title, String description, MultipartFile file) throws IOException {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    Video video = new Video();
    User authenticatedUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    video.setUser(authenticatedUser);
    video.setTitle(title);
    video.setDescription(description);
    video.setFileUrl(fileName);
    video = videoRepository.save(video);

    String uploadDir = "./video/" + video.getId();

    Path uploadPath = Paths.get(uploadDir);
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);

    }
    try (InputStream inputStream = file.getInputStream()) {

      Path filePath = uploadPath.resolve(fileName);
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

    } catch (IOException exception) {
      throw new IOException("No se pudo guardar el archivo" + fileName);
    }

    return video;
  }

  @Transactional
  public Video actualizarVideo(Integer id, VideoRequest request) throws NoSuchElementException{
    Video video = videoRepository.findById(id).get();
    video.setTitle(request.getTitle());
    video.setDescription(request.getDescription());
    videoRepository.save(video);
    return video;
  }
  
  @Transactional
  public void eliminarVideo(Integer id) throws NoSuchElementException{
    Video video = videoRepository.findById(id).get();
    videoRepository.delete(video);
  }

  private Boolean existUser(Integer userId) {
    return cuentaRepository.findById(userId).isPresent();
  }

  private Boolean existProvider(Integer proveedorId) {
    return proveedorRepository.findById(proveedorId).isPresent();
  }
}