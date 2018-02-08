package com.gateway.decoder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.decoder.exception.ConflictException;
import com.gateway.decoder.exception.NotFoundException;
import com.gateway.exception.message.ApiExceptionMessage;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom error decoder to determine what exception will to fallback like {@link com.gateway.fallback.CustomerFallbackFactory}
 */

@Component
public class GatewayErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder decoder = new Default();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        handleError(response);
        return decoder.decode(methodKey, response);
    }

    /**
     * Handle error.
     *
     * @param response
     */
    private void handleError(Response response) {
        try {
            JsonNode jsonNode = this.mapper.readTree(response.body().asInputStream());
            // Check if error is custom from other service.
            if (jsonNode.get(ApiExceptionMessage.TIMESTAMP) != null &&
                    jsonNode.get(ApiExceptionMessage.STATUS_CODE) != null &&
                    jsonNode.get(ApiExceptionMessage.ERROR) != null &&
                    jsonNode.get(ApiExceptionMessage.HTTP_STATUS) != null) {
                // Check the status.
                if (response.status() == HttpStatus.CONFLICT.value()) {
                    throw new ConflictException(jsonNode.toString());
                } else if (response.status() == HttpStatus.NOT_FOUND.value()) {
                    throw new NotFoundException(jsonNode.toString());
                }
            } else {
                // Throw other error.
                throw new RuntimeException(jsonNode.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
