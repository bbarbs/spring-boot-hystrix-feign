package com.gateway.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.decoder.exception.ConflictException;
import com.gateway.decoder.exception.NotFoundException;
import com.gateway.exception.ApiException;
import com.gateway.exception.message.ApiExceptionMessage;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.Rule;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class TestApplicationContext {

    @Rule
    public WireMockClassRule wiremock = new WireMockClassRule(wireMockConfig().dynamicPort());

    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Helper method to extract exception message.
     *
     * @param e
     * @return
     */
    protected ApiExceptionMessage decodeExceptionMessage(ApiException e) throws IOException {
        return this.objectMapper.readValue(e.getMessage(), ApiExceptionMessage.class);
    }

    /**
     * Verify the exception message.
     *
     * @param cause
     */
    protected void verifyException(Throwable cause) throws IOException {
        ApiExceptionMessage message = this.decodeExceptionMessage(new ApiException(cause.getMessage()));
        if (cause instanceof NotFoundException) {
            assertThat(message.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        } else if (cause instanceof ConflictException) {
            assertThat(message.getStatusCode()).isEqualTo(HttpStatus.CONFLICT.value());
        }
        System.out.println("Exception: " + message.toString());
    }
}
