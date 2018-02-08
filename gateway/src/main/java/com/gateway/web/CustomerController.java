package com.gateway.web;

import com.gateway.client.CustomerClient;
import com.gateway.decoder.GatewayErrorDecoder;
import com.gateway.fallback.CustomerFallbackFactory;
import com.gateway.model.Customer;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.cloud.netflix.feign.support.SpringMvcContract;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController extends ExceptionHandler {

    CustomerClient customerClient;

    public CustomerController() {
        // Create builder to add the error decoder.
        this.customerClient = HystrixFeign.builder()
                .contract(new SpringMvcContract())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .errorDecoder(new GatewayErrorDecoder())
                .target(CustomerClient.class, "http://localhost:8080", new CustomerFallbackFactory());
    }

    /**
     * Get list of customers.
     *
     * @return
     */
    @GetMapping(
            produces = APPLICATION_JSON_VALUE
    )
    public List<Customer> getAllCustomers() {
        return this.customerClient.getAllCustomers();
    }

    /**
     * Add customer.
     *
     * @param customer
     * @return
     */
    @PostMapping(
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public Customer addCustomer(@RequestBody Customer customer) {
        Customer result = null;
        try {
            result = this.customerClient.addCustomer(customer);
        } catch (Exception e) {
            this.handleCustomerException(e.getCause());
        }
        return result;
    }

    /**
     * Get customer by id.
     *
     * @param customerId
     * @return
     */
    @GetMapping(
            value = "/{customerId}",
            produces = APPLICATION_JSON_VALUE
    )
    public Customer getCustomerById(@PathVariable(name = "customerId") Long customerId) {
        Customer customer = null;
        try {
            customer = this.customerClient.getCustomerById(customerId);
        } catch (Exception e) {
            this.handleCustomerException(e.getCause());
        }
        return customer;
    }

    /**
     * Update customer by id.
     *
     * @param customerId
     * @param customer
     * @return
     */
    @PutMapping(
            value = "/{customerId}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public Customer updateCustomerById(@PathVariable(name = "customerId") Long customerId, @RequestBody Customer customer) {
        Customer result = null;
        try {
            result = this.customerClient.updateCustomerById(customerId, customer);
        } catch (Exception e) {
            this.handleCustomerException(e.getCause());
        }
        return result;
    }

    /**
     * Delete customer by id.
     *
     * @param customerId
     * @return
     */
    @DeleteMapping(
            value = "/{customerId}"
    )
    public ResponseEntity deleteCustomerById(@PathVariable(name = "customerId") Long customerId) {
        try {
            this.customerClient.deleteCustomerById(customerId);
        } catch (Exception e) {
            this.handleCustomerException(e.getCause());
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Get customer by age.
     *
     * @param age
     * @return
     */
    @GetMapping(
            params = {"age"},
            produces = APPLICATION_JSON_VALUE
    )
    public List<Customer> getCustomersByAge(@RequestParam(name = "age") int age) {
        return this.customerClient.getCustomersByAge(age);
    }
}
