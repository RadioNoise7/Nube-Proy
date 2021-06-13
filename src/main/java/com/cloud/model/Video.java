package com.cloud.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "video")
public class Video {

  @EmbeddedId
  @JsonIgnore
  private VideoLlave id;

  @ManyToOne
  @JoinColumn(name="idCuenta", insertable = false, updatable = false)
  private Cuenta cuenta;

  @ManyToOne
  @JoinColumn(name="idProveedor", insertable = false, updatable = false)
  private Proveedor proveedor;

  @Column(name = "nombreVideo")
  private String nombre_video;

  @OneToMany(mappedBy = "video")
  private List<Comentario> comentarios;

  public Video() {}

  public Video(VideoLlave id, String nombre_video) {
    this.id = id;
    this.nombre_video = nombre_video;
  }

  public void setId(VideoLlave id) {
    this.id = id;
  }

  public VideoLlave getId() {
    return this.id;
  }

  public String getNombreVideo() {
    return nombre_video;
  }

  public void setNombreVideo(String nombre_video) {
    this.nombre_video = nombre_video;
  }

  public Cuenta getCuenta() {
    return this.cuenta;
  }

  public void setCuenta(Cuenta cuenta) {
    this.cuenta = cuenta;
  }

  public Proveedor getProveedor() {
    return this.proveedor;
  }

  public void setProveedor(Proveedor proveedor) {
    this.proveedor = proveedor;
  }

  public List<Cuenta> getCuentas() {
    return cuentas;
}

public void setCuentas(List<Cuenta> cuentas) {
    this.cuentas = cuentas;
}

  @Override
  public String toString() {
    return ( "{" + this.getCuenta() + "," + this.getProveedor() + "," + this.getNombreVideo() + "}");
  }
}