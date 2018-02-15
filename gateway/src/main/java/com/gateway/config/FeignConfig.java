package com.gateway.config;

import com.gateway.client.CustomerClient;
import com.gateway.decoder.GatewayErrorDecoder;
import com.gateway.fallback.CustomerFallbackFactory;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.cloud.netflix.feign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public CustomerClient customerClient() {
        CustomerClient customerClient = HystrixFeign.builder()
                .contract(new SpringMvcContract())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .errorDecoder(new GatewayErrorDecoder())
                .target(CustomerClient.class, "http://localhost:8080", new CustomerFallbackFactory());
        return customerClient;
    }
}
