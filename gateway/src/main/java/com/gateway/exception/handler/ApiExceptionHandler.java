package com.gateway.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.exception.ApiException;
import com.gateway.exception.CustomerConflictException;
import com.gateway.exception.CustomerNotFoundException;
import com.gateway.exception.message.ApiExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ApiExceptionHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Helper method to extract exception message.
     *
     * @param e
     * @return
     */
    private ApiExceptionMessage readException(ApiException e) throws IOException {
        return this.objectMapper.readValue(e.getMessage(), ApiExceptionMessage.class);
    }

    /**
     * {@link CustomerNotFoundException} exception.
     *
     * @param e
     * @return
     * @throws IOException
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiExceptionMessage> customerNotFoundException(ApiException e) throws IOException {
        return new ResponseEntity<>(this.readException(e), HttpStatus.NOT_FOUND);
    }

    /**
     * {@link CustomerConflictException} exception.
     *
     * @param e
     * @return
     * @throws IOException
     */
    @ExceptionHandler(CustomerConflictException.class)
    public ResponseEntity<ApiExceptionMessage> customerConflictException(ApiException e) throws IOException {
        return new ResponseEntity<>(this.readException(e), HttpStatus.CONFLICT);
    }
}
