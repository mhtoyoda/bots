package com.fiveware.exception;

/**
 * Created by valdisnei on 31/05/17.
 */
public class RuntimeBotException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RuntimeBotException() {super();}

    public RuntimeBotException(String msg) {
        super(msg);
    }

    public RuntimeBotException(Throwable cause) {
        super(cause);
    }

    public RuntimeBotException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
