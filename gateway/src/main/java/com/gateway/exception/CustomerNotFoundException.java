package com.gateway.exception;

public class CustomerNotFoundException extends ApiException {

    public CustomerNotFoundException() {
    }

    public CustomerNotFoundException(String s) {
        super(s);
    }
}
