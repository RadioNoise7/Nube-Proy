package com.cloud.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


import com.cloud.model.Video;

import com.cloud.model.VideoLlave;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class VideoRest {

  @Autowired
  private VideoService videosService;

  @GetMapping("/videos")
  public ResponseEntity<List<Video>> getVideos() {
    List<Video> videos = videosService.getVideos();
    return ResponseEntity.ok().body(videos);
  }

  @GetMapping("/videos/cuentas/{cuentaId}/provedores/{provedorId}")
  public ResponseEntity<Video> getVideos(@PathVariable("cuentaId") Integer id_cuenta, @PathVariable("provedorId") Integer id_proveedor) {
    VideoLlave id = new VideoLlave(id_cuenta, id_proveedor);

    Video videos = videosService.getVideo(id);

    return ResponseEntity.ok().body(videos);
  }

  @PostMapping("/videos")
  public ResponseEntity<Video> postVideos(@RequestBody VideoRequest request, @RequestParam("file") MultipartFile file) throws URISyntaxException {
    Video videosCreada = videosService.crearVideo(request, file);

    return ResponseEntity.created(new URI("/videos/" + videosCreada.getId())).body(videosCreada);
  }

  @PutMapping("/videos/cuentas/{cuentaId}/provedores/{provedorId}")
  public ResponseEntity<Video> putVideos(@PathVariable("cuentaId") Integer id_cuenta,  @PathVariable("provedorId") Integer id_proveedor, @RequestBody @Validated VideoRequest request) {
    VideoLlave id = new VideoLlave(id_cuenta, id_proveedor);
    Video videosActualizada = videosService.actualizarVideo(id, request);

    return ResponseEntity.ok().body(videosActualizada);
  }

  @DeleteMapping("/videos/cuentas/{cuentaId}/provedores/{provedorId}")
  public ResponseEntity<Void> deleteVideos(@PathVariable("cuentaId") Integer id_cuenta, @PathVariable("provedorId") Integer id_proveedor) {
    VideoLlave id = new VideoLlave(id_cuenta, id_proveedor);

    videosService.eliminarVideo(id);

    return ResponseEntity.ok().build();
  }
}
