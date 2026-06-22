package com.caretrack.exception;

import org.springframework.http.HttpStatus;

public class ProviderNotFoundException extends RuntimeException {

    HttpStatus status;
    String message;

    public ProviderNotFoundException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
    public HttpStatus getStatus() {
        return status;
    }
    public String getMessage() {
        return  message;
    }
}
