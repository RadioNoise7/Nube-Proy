package com.cloud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(){
        super("La ruta no existe o no fue encontrada");
    }

    public ForbiddenException(String mensaje){
        super(mensaje);
    }
}
