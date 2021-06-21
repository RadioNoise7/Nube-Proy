package com.cloud.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.cloud.model.Video;

import com.cloud.model.Request.VideoRequest;
import com.cloud.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @PostMapping("/videos")
  public ResponseEntity<Video> postVideos(@RequestHeader String title, @RequestHeader String description, @RequestParam("file") MultipartFile file) throws URISyntaxException, IOException {
    Video videosCreada = videoService.crearVideo(title, description, file);

    return ResponseEntity.created(new URI("/videos/" + videosCreada.getId())).body(videosCreada);
  }

  @PutMapping("/videos/{videoId}")
  public ResponseEntity<Video> putVideos(@PathVariable("videoId") Integer videoId, @RequestBody @Validated VideoRequest request) {
    Video updatedVideo = videoService.actualizarVideo(videoId, request);
    return ResponseEntity.ok().body(updatedVideo);
  }

  @DeleteMapping("/videos/{videoId}")
  public ResponseEntity<Void> deleteVideos(@PathVariable("videoId") Integer videoId) {
    videoService.eliminarVideo(videoId);
    return ResponseEntity.ok().build();
  }
}
