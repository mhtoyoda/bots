package com.fiveware.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ApiError {
    final HttpStatus status;
    final String localizedMessage;
    String error;
    List<String> errors;

    public ApiError(HttpStatus badRequest, String localizedMessage, String error) {
        this.status = badRequest;
        this.localizedMessage = localizedMessage;
        this.error = error;
    }

    public ApiError(HttpStatus badRequest, String localizedMessage, List<String> errors) {
        this.status = badRequest;
        this.localizedMessage = localizedMessage;
        this.errors = errors;
    }
    public HttpStatus getStatus() {
        return status;
    }

    public String getLocalizedMessage() {
        return localizedMessage;
    }

    public String getError() {
        return error;
    }

    public List<String> getErrors() {
        return errors;
    }
}