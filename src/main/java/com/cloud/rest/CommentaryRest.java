package com.cloud.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.exception.ValidationExceptionsHandler;
import com.cloud.model.Commentary;
import com.cloud.service.CommentaryService;

@RestController
@RequestMapping("/api")
public class CommentaryRest {

    @Autowired
    private CommentaryService comentarioService;

    @GetMapping("/comentarios")
    public ResponseEntity<List<Commentary>> obtenerUsuario() {
        List<Commentary> comentarios = comentarioService.getComentarios();
        return ResponseEntity.ok(comentarios);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> validateExceptions(MethodArgumentNotValidException ex) {
        ValidationExceptionsHandler exHandler = new ValidationExceptionsHandler(ex);
        return exHandler.handleValidationExceptions();
    }
}
