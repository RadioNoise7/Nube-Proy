package com.cloud.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class Role /*implements Serializable*/ {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name="rolename") 
    private String rolename;

    public Integer getId(){
        return this.id; 
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getRolename(){
        return  rolename;
    }

    public void setRolename(String rolename){
        this.rolename = rolename;
    }
}
