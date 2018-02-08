package com.gateway.decoder.exception;

import feign.FeignException;

/**
 * Exception for custom handling in {@link com.gateway.decoder.GatewayErrorDecoder}
 */

public class ConflictException extends FeignException {

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(int status, String message) {
        super(status, message);
    }
}
