package com.cloud.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.model.Commentary;
import com.cloud.repository.CommentaryRepository;

@Service
public class CommentaryService {

    @Autowired
    private CommentaryRepository comentarioRepository;

    /**
    * Este es el metodo que devuelve los comentarios
    * @return comentarios
    *
    */
    public List<Commentary> getComentarios() {

        List<Commentary> comentarios = new LinkedList<>();
        comentarioRepository.findAll().iterator().forEachRemaining(comentarios::add);

        return comentarios;
    }

    /**
    * Este es el metodo que devuelve el equpo por cuenta
    * @param request Intger, id del cuenta 
    * @return void
    *
    */
    public Commentary getComentario(Integer id) {
        return comentarioRepository.findById(id).get();
    }
}
