package com.cloud.model.Request;

import javax.validation.constraints.NotBlank;

public class VideoRequest {

  @NotBlank
  private String title;

  private String description;

  public String getTitle(){
    return  title;
  }

  public String getDescription(){
    return description;
  }
}