package com.fiveware.exception;

import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class RecoverableException extends Exception{

    private final String message;


    public RecoverableException(String message) {
        super(message);
        this.message=message;
    }

    public RecoverableException(Throwable message) {
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
