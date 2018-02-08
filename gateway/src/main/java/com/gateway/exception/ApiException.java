package com.gateway.exception;

public class ApiException extends RuntimeException {

    public ApiException() {
    }

    public ApiException(String s) {
        super(s);
    }
}
