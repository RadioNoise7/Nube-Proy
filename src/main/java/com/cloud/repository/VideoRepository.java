package com.cloud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cloud.model.Video;


@Repository
public interface VideoRepository extends CrudRepository<Video, Integer> {

  public Optional<Video>findById(Integer id);
  public List<Video> findByUserId(Integer user_id);
  /*public List<Video> findByProviderId(Integer provider_id);*/

}
