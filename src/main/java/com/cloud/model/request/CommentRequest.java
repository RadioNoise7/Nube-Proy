package com.cloud.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CommentRequest {

    @NotBlank(message="Este campo es obligatorio y no puede estar vacío")
    @Size(min = 5, max = 1000, message="El campo debe tener de 5 hasta 1000 caracteres")
    private String content;

    public String getContent() {
        return content;
    }
}