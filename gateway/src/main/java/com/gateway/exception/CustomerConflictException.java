package com.gateway.exception;

public class CustomerConflictException extends ApiException {

    public CustomerConflictException() {
    }

    public CustomerConflictException(String s) {
        super(s);
    }
}
