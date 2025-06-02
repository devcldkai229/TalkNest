package com.backend.TalkNestResourceServer.exception.signature;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConfirmPasswordNotMatchException extends RuntimeException{

    public ConfirmPasswordNotMatchException(String message) {
        super(message);
    }
}
