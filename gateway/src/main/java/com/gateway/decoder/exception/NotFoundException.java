package com.gateway.decoder.exception;

public class NotFoundException extends FeignDecoderException {

    public NotFoundException() {
    }

    public NotFoundException(String s) {
        super(s);
    }
}
