package com.cloud.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import com.cloud.model.Videos;
import com.cloud.model.VideosLlave;
import com.cloud.model.Request.VideosRequest;
import com.cloud.service.VideosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class VideosRest {

  @Autowired
  private VideosService videosService;

  @GetMapping("/videos")
  public ResponseEntity<List<Videos>> getVideos() {
    List<Videos> videos = videosService.getVideos();
    return ResponseEntity.ok().body(videos);
  }

  @GetMapping("/videos/cuentas/{cuentaId}/provedores/{provedorId}")
  public ResponseEntity<Videos> getVideos(@PathVariable("cuentaId") Integer id_cuenta, @PathVariable("provedorId") Integer id_proveedor) {
    VideosLlave id = new VideosLlave(id_cuenta, id_proveedor);

    Videos videos = videosService.getVideos(id);

    return ResponseEntity.ok().body(videos);
  }

  @PostMapping("/videos")
  public ResponseEntity<Videos> postVideos(@RequestBody VideosRequest request) throws URISyntaxException {
    Videos videosCreada = videosService.crearVideos(request);

    return ResponseEntity.created(new URI("/videos/" + videosCreada.getId())).body(videosCreada);
  }

  @PutMapping("/videos/cuentas/{cuentaId}/provedores/{provedorId}")
  public ResponseEntity<Videos> putVideos(@PathVariable("cuentaId") Integer id_cuenta, @PathVariable("provedorId") Integer id_proveedor, @RequestBody @Valid VideosRequest request) {
    VideosLlave id = new VideosLlave(id_cuenta, id_proveedor);

    Videos videosActualizada = videosService.actualizarVideos(id, request);

    return ResponseEntity.ok().body(videosActualizada);
  }

  @DeleteMapping("/videos/cuentas/{cuentaId}/provedores/{provedorId}")
  public ResponseEntity<Void> deleteVideos(@PathVariable("cuentaId") Integer id_cuenta, @PathVariable("provedorId") Integer id_proveedor) {
    VideosLlave id = new VideosLlave(id_cuenta, id_proveedor);

    videosService.eliminarVideos(id);

    return ResponseEntity.ok().build();
  }
}
