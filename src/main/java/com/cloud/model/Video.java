package com.cloud.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "videos")
public class Video implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name="user_id")
  private User user;

  @NotBlank
  private String title;

  private String description;

  @Column(name = "file_url")
  private String fileUrl;

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getFileUrl() {
    return this.fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  @Transient
  public String getVideoPath() {
    if(fileUrl==null)return null;
    return fileUrl; 
  }

  @Override
  public String toString() {
    return ( "{" + this.getUser() + "," + this.getTitle() + "," + this.getDescription() + "," +this.getFileUrl() +"}");
  }
}