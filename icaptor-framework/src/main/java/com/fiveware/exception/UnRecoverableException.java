package com.fiveware.exception;

import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class UnRecoverableException extends Exception{

    private final String message;


    public UnRecoverableException(String message) {
        super(message);
        this.message=message;
    }

    public UnRecoverableException(Throwable message) {
        super(message);
        this.message=message.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
