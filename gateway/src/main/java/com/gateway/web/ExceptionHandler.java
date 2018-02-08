package com.gateway.web;

import com.gateway.decoder.exception.ConflictException;
import com.gateway.decoder.exception.NotFoundException;
import com.gateway.exception.CustomerConflictException;
import com.gateway.exception.CustomerNotFoundException;

public abstract class ExceptionHandler {

    /**
     * Handle exceptions related to customer.
     *
     * @param cause
     */
    protected void handleCustomerException(Throwable cause) {
        if (cause instanceof NotFoundException) {
            throw new CustomerNotFoundException(cause.getMessage());
        } else if (cause instanceof ConflictException) {
            throw new CustomerConflictException(cause.getMessage());
        }
    }
}
