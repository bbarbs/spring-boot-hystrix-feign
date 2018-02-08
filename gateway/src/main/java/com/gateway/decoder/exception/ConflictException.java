package com.gateway.decoder.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends FeignDecoderException {

    public ConflictException() {
    }

    public ConflictException(String s) {
        super(s);
    }
}
