package com.gateway.fallback;

import com.gateway.client.CustomerClient;
import com.gateway.decoder.exception.ConflictException;
import com.gateway.decoder.exception.NotFoundException;
import com.gateway.model.Customer;
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
                handleFeignException(cause);
                return new ArrayList<>();
            }

            @Override
            public Customer addCustomer(Customer customer) {
                handleFeignException(cause);
                return new Customer();
            }

            @Override
            public Customer getCustomerById(Long customerId) {
                handleFeignException(cause);
                return new Customer();

            }

            @Override
            public Customer updateCustomerById(Long customerId, Customer customer) {
                handleFeignException(cause);
                return new Customer();
            }

            @Override
            public void deleteCustomerById(Long customerId) {
                handleFeignException(cause);
            }

            @Override
            public List<Customer> getCustomersByAge(int age) {
                handleFeignException(cause);
                return new ArrayList<>();
            }
        };
    }

    /**
     * Helper method to handle exception thrown by {@link com.gateway.decoder.GatewayErrorDecoder}.
     *
     * @param cause
     */
    private void handleFeignException(Throwable cause) {
        if (cause instanceof NotFoundException ||
                cause instanceof ConflictException) {
            // Throw exception to propagate the error to controller.
            throw new RuntimeException(cause.getMessage());
        }
    }
}
