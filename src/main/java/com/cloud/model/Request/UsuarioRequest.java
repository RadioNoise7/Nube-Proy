package com.cloud.model.Request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cloud.model.Tipo;

public class UsuarioRequest {
    
    @NotNull
    @Size(min = 1, max = 255)
    @NotEmpty
    private String nombre;

    @NotNull
    @Size(min = 5, max = 50)
    @NotEmpty
    private String usuario;

    @NotNull
    @Size(min = 5, max = 50)
    @NotEmpty
    private String password;

    @Email
    @NotEmpty
    @NotNull
    private String correo;
    
    @NotEmpty
    @NotNull
    private Tipo tipo;

    public UsuarioRequest() {

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tipo getTipo() {
        return this.tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo; 
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo; 
    }
}