package com.cloud.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cloud.model.Provider;
import com.cloud.model.Role;

public class AccountRequest {

    @NotEmpty
    @Size(min = 1, max = 255)
    private String nombre;

    @NotNull
    private Provider proveedor;

    @Email
    @NotNull
    private String correo;

    private Role tipo; 

    public AccountRequest() {
    }

    public AccountRequest(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Role getTipo() {
        return this.tipo;
    }

    public void setTipo(Role tipo) {
        this.tipo = tipo;
    }

    public AccountRequest nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Provider getProveedor() {
        return this.proveedor;
    }

    public void setProveedor(Provider proveedor) {
        this.proveedor = proveedor;
    }

}
