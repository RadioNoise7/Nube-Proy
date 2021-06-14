package com.cloud.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VideoLlave implements Serializable {

  @Column(name="idCuenta")
  private Integer id_cuenta;

  @Column(name="idProveedor")
  private Integer id_proveedor;

  public VideoLlave() {}

  public VideoLlave(Integer id_cuenta, Integer id_proveedor) {
    this.id_cuenta = id_cuenta;
    this.id_proveedor = id_proveedor;
  }

  public void setIdCuenta(Integer id_cuenta) {
    this.id_cuenta = id_cuenta;
  }

  public Integer getIdCuenta() {
    return this.id_cuenta;
  }

  public void setIdProveedor(Integer id_proveedor) {
    this.id_proveedor = id_proveedor;
  }

  public Integer getIdProveedor() {
    return this.id_proveedor;
  }
}
