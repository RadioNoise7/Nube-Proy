package com.cloud.rest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import com.cloud.exception.ValidationExceptionsHandler;
import com.cloud.model.Video;
import com.cloud.model.request.VideoRequest;

import com.cloud.service.VideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class VideoRest {

  @Autowired
  private VideoService videoService;

/*-----------------------------Nivel administrador------------------------------*/
  @GetMapping("/videos/all")
  public ResponseEntity<List<Video>> getAllVideos() {
    List<Video> videos = videoService.getAllVideos();
    return ResponseEntity.ok().body(videos);
  }

  @GetMapping("/videos/usuarios/{userId}")
  public ResponseEntity<List<Video>> getVideosByUserId(@PathVariable("userId") Integer userId) {
    List<Video> videos = videoService.getVideosByUserId(userId);
    return ResponseEntity.ok().body(videos);
  }

  /*-----------------------------Nivel usuario común-----------------------------*/
  @GetMapping("/videos")
  public ResponseEntity<List<Video>> getUserVideos() {
    List<Video> videos = videoService.getUserVideos();

    return ResponseEntity.ok().body(videos);
  }

  @PostMapping(value= "/videos", consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<Video> postVideos(@RequestPart String video, @RequestPart("file") MultipartFile file) throws URISyntaxException, IOException {
    Video videosCreada = videoService.crearVideo(video, file);

    return ResponseEntity.created(new URI("/videos/" + videosCreada.getId())).body(videosCreada);
  }

  @GetMapping("/videos/{id}/completo")
  public ResponseEntity<UrlResource> getFullVideo(@PathVariable Integer id) throws MalformedURLException {
      UrlResource video = videoService.getVideoResource(id);

      return ResponseEntity.status(HttpStatus.OK)
              .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
              .body(video);
  }

  
  @GetMapping("/videos/{videoId}")
  public ResponseEntity<ResourceRegion> getVideoByParts(@PathVariable Integer videoId,
  @RequestHeader HttpHeaders headers) throws MalformedURLException {
    
      UrlResource videoResource = videoService.getVideoResource(videoId);
      ResourceRegion videoRegion = videoService.getVideoResourceInParts(videoResource, headers);

      return ResponseEntity.status(HttpStatus.OK)
              .contentType(MediaTypeFactory.getMediaType(videoResource).orElse(MediaType.APPLICATION_OCTET_STREAM))
              .body(videoRegion);
  }

  @PutMapping("/videos/{videoId}")
  public ResponseEntity<Video> putVideos(@PathVariable("videoId") Integer videoId, @RequestBody @Validated VideoRequest request) {
    Video updatedVideo = videoService.updateVideo(videoId, request);
    return ResponseEntity.ok().body(updatedVideo);
  }

  @DeleteMapping("/videos/{videoId}")
  public ResponseEntity<Void> deleteVideos(@PathVariable("videoId") Integer videoId) {
    videoService.deleteVideo(videoId);
    return ResponseEntity.ok().build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> validateExceptions(MethodArgumentNotValidException ex) {
    ValidationExceptionsHandler exHandler = new ValidationExceptionsHandler(ex);
    return exHandler.handleValidationExceptions();
  }
}
