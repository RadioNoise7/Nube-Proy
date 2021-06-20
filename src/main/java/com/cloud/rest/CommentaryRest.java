package com.cloud.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.model.Commentary;
import com.cloud.service.CommentaryService;

@RestController
@RequestMapping("/api")
public class CommentaryRest{

    @Autowired
    private CommentaryService comentarioService;

    @GetMapping("/comentarios")
    public ResponseEntity<List<Commentary>> obtenerUsuario() {
        List<Commentary> comentarios = comentarioService.getComentarios();
        return ResponseEntity.ok(comentarios);
    }
    
}
