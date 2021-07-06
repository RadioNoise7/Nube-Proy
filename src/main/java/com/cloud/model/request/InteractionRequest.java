package com.cloud.model.Request;

import javax.validation.constraints.NotNull;

import jdk.jfr.BooleanFlag;

public class InteractionRequest {

    @NotNull(message = "El campo no puede estar vacío")
    @BooleanFlag
    private Boolean status;

    public Boolean getStatus() {
        return status;
    }
}
