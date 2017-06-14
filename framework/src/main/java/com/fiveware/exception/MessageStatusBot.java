package com.fiveware.exception;

/**
 * Created by valdisnei on 30/05/17.
 */
public class MessageStatusBot {

    private final Integer status;
    private final String message;

    public MessageStatusBot(final Integer status, final String message) {
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
