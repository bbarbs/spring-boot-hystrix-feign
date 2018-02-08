package com.gateway.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.exception.ApiException;
import com.gateway.exception.CustomerConflictException;
import com.gateway.exception.CustomerNotFoundException;
import com.gateway.exception.message.ApiExceptionMessage;
import com.gateway.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @Autowired
    TextUtil textUtil;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Helper method to extract exception message.
     *
     * @param e
     * @return
     */
    private ApiExceptionMessage getExceptionMessage(ApiException e) throws IOException {
        String errorMessage = this.textUtil.extractJson(e.getLocalizedMessage(), 2);
        return this.objectMapper.readValue(errorMessage, ApiExceptionMessage.class);
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
        return new ResponseEntity<>(getExceptionMessage(e), HttpStatus.NOT_FOUND);
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
        return new ResponseEntity<>(getExceptionMessage(e), HttpStatus.CONFLICT);
    }
}
