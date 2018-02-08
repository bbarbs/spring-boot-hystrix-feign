package com.gateway.decoder.exception;

import feign.FeignException;

/**
 * Exception for custom handling in {@link com.gateway.decoder.GatewayErrorDecoder}
 */

public class NotFoundException extends FeignException {

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(int status, String message) {
        super(status, message);
    }
}
