package com.backend.TalkNestResourceServer.exception.signature;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthenticatedException extends RuntimeException{

    public UnauthenticatedException(String message) {
        super(message);
    }

}
