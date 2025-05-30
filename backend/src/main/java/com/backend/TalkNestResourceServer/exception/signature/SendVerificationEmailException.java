package com.backend.TalkNestResourceServer.exception.signature;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SendVerificationEmailException extends RuntimeException{

    public SendVerificationEmailException(String e) {
        super(e);
    }
}
