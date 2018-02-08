package com.customer.exception.handler;

import com.customer.exception.ApiException;
import com.customer.exception.CustomerExistsException;
import com.customer.exception.CustomerNotFoundException;
import com.customer.exception.message.ApiExceptionMessage;
import com.customer.exception.message.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Error response for {@link CustomerNotFoundException}
     *
     * @param e
     * @return
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiExceptionMessage> customerNotFoundException(ApiException e) {
        return new ResponseEntity<>(new ApiExceptionMessage(
                new Date(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                new ErrorMessage(e.getMessage())
        ), HttpStatus.NOT_FOUND);
    }

    /**
     * Error response for {@link CustomerExistsException}
     *
     * @param e
     * @return
     */
    @ExceptionHandler(CustomerExistsException.class)
    public ResponseEntity<ApiExceptionMessage> customerExistsException(ApiException e) {
        return new ResponseEntity<>(new ApiExceptionMessage(
                new Date(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT,
                new ErrorMessage(e.getMessage())
        ), HttpStatus.CONFLICT);
    }
}
