package com.cloud.rest;

import java.util.List;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.model.User;
import com.cloud.service.UserService;

@RestController
@RequestMapping("/api")
public class UserRest {

    @Autowired
    private UserService usuarioService;

    @GetMapping("/quienSoy") // /self
    public ResponseEntity<User> getLoggedUser() {
        User usuario = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<User>> obtenerUsuario() {
        List<User> usuarios = usuarioService.getUsers();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<User> getUsuario(@PathVariable Integer id) {
        User user = usuarioService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

   

}