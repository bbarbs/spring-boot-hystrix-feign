package com.gateway.fallback;

import com.gateway.client.CustomerClient;
import com.gateway.model.Customer;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerFallbackFactory implements FallbackFactory<CustomerClient> {

    @Override
    public CustomerClient create(Throwable cause) {
        return new CustomerClient() {
            @Override
            public List<Customer> getAllCustomers() {
                handleErrorDecoderException(cause);
                return new ArrayList<>();
            }

            @Override
            public Customer addCustomer(Customer customer) {
                handleErrorDecoderException(cause);
                return new Customer();
            }

            @Override
            public Customer getCustomerById(Long customerId) {
                handleErrorDecoderException(cause);
                return new Customer();

            }

            @Override
            public Customer updateCustomerById(Long customerId, Customer customer) {
                handleErrorDecoderException(cause);
                return new Customer();
            }

            @Override
            public void deleteCustomerById(Long customerId) {
                handleErrorDecoderException(cause);
            }

            @Override
            public List<Customer> getCustomersByAge(int age) {
                handleErrorDecoderException(cause);
                return new ArrayList<>();
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
}
