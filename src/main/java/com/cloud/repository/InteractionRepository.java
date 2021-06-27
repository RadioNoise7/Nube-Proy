package com.cloud.repository;

import java.util.List;
import java.util.Optional;

import com.cloud.model.Interaction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionRepository extends CrudRepository<Interaction, Integer> {
    Optional<Interaction> findByUserIdAndVideoId(Integer userId, Integer videoId);
    
    Optional<List<Interaction>> findByUserId(Integer userId);

    Optional<List<Interaction>> findByVideoId(Integer videoId);
}
