package com.gateway.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.decoder.exception.ConflictException;
import com.gateway.decoder.exception.NotFoundException;
import com.gateway.exception.message.ApiExceptionMessage;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Custom error decoder to determine what exception will to fallback like {@link com.gateway.fallback.CustomerFallbackFactory}
 */

@Component
public class GatewayErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder decoder = new Default();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        String message = this.getExceptionMessage(response);
        if (response.status() == HttpStatus.CONFLICT.value()) {
            throw new ConflictException(message);
        } else if (response.status() == HttpStatus.NOT_FOUND.value()) {
            throw new NotFoundException(message);
        }
        return decoder.decode(methodKey, response);
    }

    /**
     * Helper method to get the exception message.
     * Same error format is thrown from other service which is {@link ApiExceptionMessage}
     *
     * @param response
     * @return
     */
    private String getExceptionMessage(Response response) {
        String body = null;
        try {
            ApiExceptionMessage exceptionMessage = mapper.readValue(response.body().asInputStream(), ApiExceptionMessage.class);
            body = mapper.writeValueAsString(exceptionMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }
}
