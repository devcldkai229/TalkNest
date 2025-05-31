package com.backend.TalkNestResourceServer.exception.common;

public class NotFoundException extends BaseException{

    private int statusCode;

    public NotFoundException(String message) {
        super(message);
    }
}
