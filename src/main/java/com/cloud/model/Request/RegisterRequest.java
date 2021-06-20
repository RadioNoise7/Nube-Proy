package com.cloud.model.Request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank
    @Size(min = 5, max = 50)
    private String username;

    @NotBlank
    @Pattern(regexp="^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$&*]).{8,}$", message="La contrasea debe tener al menos 8 caracteres, una letra, un numero y un simbolo especial")
    private String password;

    @NotBlank
    @Size(min = 5, max = 255)
    private String accountname;

    @NotBlank
    @Email
    private String email;
    

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountname(){
        return accountname;
    }

    public String getEmail(){
        return email;
    }
}
