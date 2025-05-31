package com.backend.TalkNestResourceServer.exception.common;


public abstract class BaseException extends RuntimeException{

    public BaseException(String message) {
        super(message);
    }

}
