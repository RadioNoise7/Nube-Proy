package com.cloud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cloud.model.Commentary;

@Repository
public interface CommentaryRepository extends CrudRepository<Commentary, Integer> {

}