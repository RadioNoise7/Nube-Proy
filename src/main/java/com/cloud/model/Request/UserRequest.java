package com.cloud.model.Request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cloud.model.Role;

public class UserRequest {
    
    @NotNull
    @Size(min = 1, max = 255)
    @NotEmpty
    private String username;

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
    private Role tipo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo; 
    }

    public Role getTipo() {
        return this.tipo;
    }

    public void setTipo(Role tipo) {
        this.tipo = tipo; 
    }

}