package com.backend.TalkNestResourceServer.exception.signature;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class VerifyTokenFailedException extends RuntimeException{

    public VerifyTokenFailedException(String message) {
        super(message);
    }
}
