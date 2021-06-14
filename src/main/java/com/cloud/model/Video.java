package com.cloud.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.Transient;

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

  @Column(name = "fileVideo")
  private String fileVideo;

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

  public Cuenta getCuentas() {
    return this.cuenta;
  }
  public void setCuentas(Cuenta cuentas) {
    this.cuenta = cuentas;
  }
  
  public String getFileVideo() {
    return fileVideo;
  }

  public void setFileVideo(String fileVideo) {
    this.fileVideo = fileVideo;
  }

  @Transient
  public String getVideoPath() {
    if(fileVideo==null || id==null)return null;
    return "/video/"+ id +"/"+fileVideo; 
  }

  @Override
  public String toString() {
    return ( "{" + this.getCuenta() + "," + this.getProveedor() + "," + this.getNombreVideo() + "}");
  }
}