package com.cloud.model.Request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class VideoRequest implements Serializable {

  @NotBlank(message="Este campo es obligatorio y no puede estar vacío")
  @Size(min = 1, max = 100, message="El campo debe tener de 1 hasta 100 caracteres")
  private String title;

  @NotNull(message="Este campo es obligatorio pero puede estar vacío")
  @Size(min = 0, max = 1000, message="El campo debe tener de 0 hasta 100 caracteres")
  private String description;

  public VideoRequest(){}
  
  public String getTitle(){
    return  title;
  }

  public String getDescription(){
    return description;
  }
}