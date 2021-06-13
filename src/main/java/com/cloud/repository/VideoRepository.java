package com.cloud.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cloud.model.Video;
import com.cloud.model.VideoLlave;


@Repository
public interface VideoRepository extends CrudRepository<Video, VideoLlave> {
  List<Video> findByCuentaId(Integer id_cuenta);
  List<Video> findByProveedorId(Integer id_proveedor);
}
