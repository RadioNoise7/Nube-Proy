package com.cloud.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "interactions")
public class Interaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "video_id")
    private Video video;

    @Column(name = "like_status")
    private Boolean likeStatus;

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public Video getVideo(){
        return video;
    }

    public void setVideo(Video video){
        this.video = video;
    }

    public Boolean getLikeStatus(){
        return likeStatus;
    }

    public void setLikeStatus(Boolean status){
        this.likeStatus = status;
    }
}
