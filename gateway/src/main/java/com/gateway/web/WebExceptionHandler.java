package com.gateway.web;

import com.gateway.decoder.exception.ConflictException;
import com.gateway.decoder.exception.NotFoundException;
import com.gateway.exception.CustomerConflictException;
import com.gateway.exception.CustomerNotFoundException;

public abstract class WebExceptionHandler {

    /**
     * Handle exceptions related to customer.
     * <p>
     * Note: It must get the cause of the error and not the present.
     *
     * @param cause
     */
    protected void handleException(Throwable cause) {
        if (cause instanceof NotFoundException) {
            throw new CustomerNotFoundException(cause.getMessage());
        } else if (cause instanceof ConflictException) {
            throw new CustomerConflictException(cause.getMessage());
        } else {
            throw new RuntimeException("Service down");
        }
    }
}
