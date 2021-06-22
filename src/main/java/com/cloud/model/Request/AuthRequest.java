package com.cloud.model.request;

import javax.validation.constraints.NotBlank;

import java.io.Serializable;


public class AuthRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
    

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return this.password;
    }

}