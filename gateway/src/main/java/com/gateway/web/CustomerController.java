package com.gateway.web;

import com.gateway.client.CustomerClient;
import com.gateway.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController extends WebExceptionHandler {

    @Autowired
    CustomerClient customerClient;

    /**
     * Get list of customers.
     *
     * @return
     */
    @GetMapping(
            produces = APPLICATION_JSON_VALUE
    )
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        try {
            list = this.customerClient.getAllCustomers();
        } catch (Exception e) {
            this.handleCustomerException(e.getCause());
        }
        return list;
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
        List<Customer> list = new ArrayList<>();
        try {
            list = this.customerClient.getCustomersByAge(age);
        } catch (Exception e) {
            this.handleCustomerException(e.getCause());
        }
        return list;
    }
}
