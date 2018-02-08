package com.customer.exception;

public class CustomerExistsException extends ApiException {

    public CustomerExistsException() {
    }

    public CustomerExistsException(String s) {
        super(s);
    }
}
