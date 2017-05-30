package com.fiveware.service;

/**
 * Created by valdisnei on 30/05/17.
 */
public class MessageBot {

    private final Integer status;
    private final String message;

    public MessageBot(final Integer status, final String message) {
        this.status = status;
        this.message=message;
    }

    public String getMessage() {
        return this.message;
    }

    public Integer getStatus() {
        return status;
    }
}
