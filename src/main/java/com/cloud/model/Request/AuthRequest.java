package com.cloud.model.Request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.io.Serializable;


import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

import com.cloud.model.Cuenta;

public class AuthRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    @NotEmpty
    @NotNull
    private String usuario;
    
    @NotEmpty
    @NotNull
    private String nombre;

    @NotNull
    @Pattern(regexp="^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$&*]).{8,}$",message="La contrasea debe tener al menos 8 caracteres, una letra, un numero y un simbolo especial")
    @NotEmpty
    private String password;
 
    @Email
    @NotEmpty
    @NotNull
    private String correo;
    
    public AuthRequest() {
    }

    public AuthRequest(String usuario, String password) {
        this.setUsuario(usuario);
        this.setPassword(password);
    }


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthRequest(String nombre) {
        this.nombre = nombre;
    }

    public AuthRequest nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    //fin nuevo
    @Override
    public String toString() {
        return "{" +
            " nombre='" + getUsuario() + "'" +
            "}";
    }
    
}