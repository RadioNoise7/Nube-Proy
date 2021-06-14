package com.cloud.model.Request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.cloud.model.VideoLlave;

public class VideoRequest {

  @NotNull
  private VideoLlave id;

  public VideoLlave getId() {
    return id;
  }

  public void setId(VideoLlave id) {
    this.id = id;
  }
}