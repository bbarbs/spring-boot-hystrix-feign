package com.gateway.fallback;

import com.gateway.client.CustomerClient;
import com.gateway.model.Customer;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CustomerFallbackFactory implements FallbackFactory<CustomerClient> {

    @Override
    public CustomerClient create(Throwable cause) {
        return new CustomerClient() {
            @Override
            public List<Customer> getAllCustomers() {
                handleErrorDecoderException(cause);
                return Arrays.asList(fallbackData());
            }

            @Override
            public Customer addCustomer(Customer customer) {
                handleErrorDecoderException(cause);
                return fallbackData();
            }

            @Override
            public Customer getCustomerById(Long customerId) {
                handleErrorDecoderException(cause);
                return fallbackData();
            }

            @Override
            public Customer updateCustomerById(Long customerId, Customer customer) {
                handleErrorDecoderException(cause);
                return fallbackData();
            }

            @Override
            public void deleteCustomerById(Long customerId) {
                handleErrorDecoderException(cause);
            }

            @Override
            public List<Customer> getCustomersByAge(int age) {
                handleErrorDecoderException(cause);
                return Arrays.asList(fallbackData());
            }
        };
    }

    /**
     * Helper method to handle exception thrown by {@link com.gateway.decoder.GatewayErrorDecoder}.
     *
     * @param cause
     */
    private void handleErrorDecoderException(Throwable cause) {
        // Throw exception to propagate the error to controller.
        if (cause instanceof FeignException) {
            throw new RuntimeException(cause.getMessage());
        }
    }

    /**
     * Helper method to return fallback data.
     *
     * @return
     */
    private Customer fallbackData() {
        return new Customer(1L, Customer.FALLBACK, Customer.FALLBACK, 0);
    }
}
