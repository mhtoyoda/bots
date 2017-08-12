package com.fiveware.exception;

/**
 * Created by valdisnei on 31/05/17.
 */
public class ExceptionBot extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExceptionBot() {super();}

    public ExceptionBot(String msg) {
        super(msg);
    }

    public ExceptionBot(Throwable cause) {
        super(cause);
    }

    public ExceptionBot(String msg, Throwable cause) {
        super(msg, cause);
    }
}
