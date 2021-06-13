package com.cloud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cloud.model.Comentario;

@Repository
public interface ComentarioRepository extends CrudRepository<Comentario, Integer> {

}