package com.cloud.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.cloud.exception.NotFoundException;
import com.cloud.model.Cuenta;
import com.cloud.model.Proveedor;
import com.cloud.model.Video;
import com.cloud.model.VideoLlave;

import com.cloud.model.Request.VideoRequest;
import com.cloud.repository.CuentaRepository;
import com.cloud.repository.ProveedorRepository;
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
  private CuentaRepository cuentaRepository;
  @Autowired
  private ProveedorRepository proveedorRepository;

  
  public List<Video> getVideos() {
    List<Video> videos = new LinkedList<>();

    videoRepository.findAll().iterator().forEachRemaining(videos::add);

    return videos;
  }

  public Video getVideo(VideoLlave id) {
    Optional<Video> videoEncontrada = videoRepository.findById(id);

    if (!videoEncontrada.isPresent()) {
      throw new NotFoundException();
    }

    return videoEncontrada.get();
  }

  public List<Video> getVideoByIdCuenta(Integer cuentaId) {
    List<Video> videos = new LinkedList<>();

    videos = videoRepository.findByCuentaId(cuentaId);

    return videos;
  }

  public List<Video> getVideoByIdProveedor(Integer proveedorId) {
    List<Video> videos = new LinkedList<>();

    videos = videoRepository.findByProveedorId(proveedorId);

    return videos;
  }

  @Transactional
  public Video crearVideo(VideoRequest request, MultipartFile file) throws IOException{
    String fileName= StringUtils.cleanPath(file.getOriginalFilename());

    Video video = new Video();

    Cuenta cuenta = this.cuentaExist(request.getId().getIdCuenta());
    Proveedor proveedor = this.proveedorExist(request.getId().getIdProveedor());

    video.setId(request.getId());
    video.setCuenta(cuenta);
    video.setProveedor(proveedor);
    video.setHoras(request.getHoras());
    video.setFileVideo(fileName);
    video = videoRepository.save(video);

    String uploadDir= "./video/"+ video.getId();

    Path uploadPath= Paths.get(uploadDir);
    if(!Files.exists(uploadPath)){
      Files.createDirectories(uploadPath);

    }
    try(InputStream inputStream= file.getInputStream()){
      
    Path filePath= uploadPath.resolve(fileName);
    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

    }catch(IOException exception){
      throw new IOException("No se pudo guardar el archivo" + fileName);
    }

    

  
    

    return video;
  }

  private Cuenta cuentaExist(Integer cuentaId) {
    Optional<Cuenta> cuentaExist = cuentaRepository.findById(cuentaId);

    if(!cuentaExist.isPresent()) {
      throw new NotFoundException("El cuenta no pudo ser encontrado");
    }

    return cuentaExist.get();
  }

  private Proveedor proveedorExist(Integer proveedorId) {
    Optional<Proveedor> proveedorExist = proveedorRepository.findById(proveedorId);

    if(!proveedorExist.isPresent()) {
      throw new NotFoundException("El proveedor no pudo ser encontrado");
    }

    return proveedorExist.get();
  }

  @Transactional
  public void eliminarVideo(VideoLlave id) {
    Video videoEliminada = getVideo(id);

    videoRepository.delete(videoEliminada);
  }

  @Transactional
  public Video actualizarVideo(VideoLlave id, VideoRequest request) {
    Video videoEncontrada = getVideo(id);

    videoEncontrada.setHoras(request.getHoras());
    videoRepository.save(videoEncontrada);

    return videoEncontrada;
  }
}