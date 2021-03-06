package com.cloud.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "providers")
public class Provider  implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "providername")
    private String providername;

    @Column(name = "providerUrl")
    private String urlProveedor;

    public Provider() { }

    public Provider(Integer id,String nombreProveedor,String urlProveedor){
        this.id = id;
        this.providername = nombreProveedor;
        this.urlProveedor = urlProveedor;
    }

    public Integer getId(){
        return this.id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getNombreProveedor() {
        return this.providername;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.providername = nombreProveedor;
    }

    public String getUrlProveedor() {
        return urlProveedor;
    }

    public void setUrlProveedor(String urlProveedor) {
        this.urlProveedor = urlProveedor;
    }


}