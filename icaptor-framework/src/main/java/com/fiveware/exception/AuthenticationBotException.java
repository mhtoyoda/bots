package com.fiveware.exception;

public class AuthenticationBotException extends Exception {

    private static final long serialVersionUID = 1L;

    public AuthenticationBotException() {super();}

    public AuthenticationBotException(String msg) {
        super(msg);
    }

    public AuthenticationBotException(Throwable cause) {
        super(cause);
    }

    public AuthenticationBotException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
