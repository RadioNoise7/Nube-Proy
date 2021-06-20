package com.cloud.model.Request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ProviderRequest {
    
    @NotEmpty(message = "El nombre no puede estar vacío.")
    @Size(min = 1, max = 255)
    private String nombre;

    private String urlProveedor;

    public ProviderRequest() {
    }

    public ProviderRequest(String nombre, String urlProveedor) {
        this.nombre = nombre;
        this.urlProveedor = urlProveedor;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ProviderRequest nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getUrlProveedor() {
        return this.urlProveedor;
    }

    public void setUrlProveedor(String urlProveedor) {
        this.urlProveedor = urlProveedor;
    }

    public ProviderRequest urlProveedor(String urlProveedor){
        this.urlProveedor = urlProveedor;
        return this;
    }
}