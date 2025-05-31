package com.backend.TalkNestResourceServer.exception.signature;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ExpiredTokenException extends RuntimeException{

    public ExpiredTokenException(String message) {
        super(message);
    }

}
