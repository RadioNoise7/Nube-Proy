package com.cloud.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.model.Comentario;
import com.cloud.service.ComentarioService;

@RestController
@RequestMapping("/api")
public class ComentarioRest{

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/comentarios")
    public ResponseEntity<List<Comentario>> obtenerUsuario() {
        List<Comentario> comentarios = comentarioService.getComentarios();
        return ResponseEntity.ok(comentarios);
    }
    
}
