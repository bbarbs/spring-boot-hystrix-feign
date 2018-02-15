package com.gateway.client;

import com.gateway.fallback.CustomerFallbackFactory;
import com.gateway.model.Customer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Customers rest api.
 * <p>
 * Note: It use the ribbon for client load balancer as added in yml.
 */

@FeignClient(
        name = "customers",
        fallbackFactory = CustomerFallbackFactory.class
)
public interface CustomerClient {

    /**
     * Get list of customers.
     *
     * @return
     */
    @GetMapping(
            value = "/customers",
            produces = APPLICATION_JSON_VALUE
    )
    List<Customer> getAllCustomers();

    /**
     * Add customer.
     *
     * @param customer
     * @return
     */
    @PostMapping(
            value = "/customers",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    Customer addCustomer(@RequestBody Customer customer);

    /**
     * Get customer by id.
     *
     * @param customerId
     * @return
     */
    @GetMapping(
            value = "/customers/{customerId}",
            produces = APPLICATION_JSON_VALUE
    )
    Customer getCustomerById(@PathVariable(name = "customerId") Long customerId);

    /**
     * Update customer by id.
     *
     * @param customerId
     * @param customer
     * @return
     */
    @PutMapping(
            value = "/customers/{customerId}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    Customer updateCustomerById(@PathVariable(name = "customerId") Long customerId, @RequestBody Customer customer);

    /**
     * Delete customer by id.
     *
     * @param customerId
     */
    @DeleteMapping(
            value = "/customers/{customerId}"
    )
    void deleteCustomerById(@PathVariable(name = "customerId") Long customerId);

    /**
     * Get customers by age.
     *
     * @param age
     * @return
     */
    @GetMapping(
            value = "/customers",
            params = {"age"},
            produces = APPLICATION_JSON_VALUE
    )
    List<Customer> getCustomersByAge(@RequestParam(name = "age") int age);
}
