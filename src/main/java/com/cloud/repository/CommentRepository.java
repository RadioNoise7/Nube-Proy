package com.cloud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.cloud.model.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    
    Optional<List<Comment>> findByUserId(Integer userId);

    Optional<List<Comment>> findByVideoId(Integer videoId);
}