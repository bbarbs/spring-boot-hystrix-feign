package com.customer.web;

import com.customer.exception.CustomerNotFoundException;
import com.customer.model.Customer;
import com.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    CustomerRepository repository;

    /**
     * Get all customer.
     *
     * @return
     */
    @GetMapping(
            produces = APPLICATION_JSON_VALUE
    )
    public List<Customer> getAllCustomers() {
        return this.repository.findAll();
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
        return this.repository.save(customer);
    }

    /**
     * Get customer by id
     *
     * @param customerId
     * @return
     * @throws CustomerNotFoundException
     */
    @GetMapping(
            value = "/{customerId}",
            produces = APPLICATION_JSON_VALUE
    )
    public Customer getCustomerById(@PathVariable(name = "customerId") Long customerId) throws CustomerNotFoundException {
        Customer customer = this.repository.findOne(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        return customer;
    }

    /**
     * Delete customer by id.
     *
     * @param customerId
     * @return
     * @throws CustomerNotFoundException
     */
    @DeleteMapping(
            value = "/{customerId}"
    )
    public ResponseEntity deleteCustomerById(@PathVariable(name = "customerId") Long customerId) throws CustomerNotFoundException {
        if (!this.repository.exists(customerId)) {
            throw new CustomerNotFoundException("Customer not found");
        }
        this.repository.delete(customerId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Update customer by id.
     *
     * @param customerId
     * @param customer
     * @return
     * @throws CustomerNotFoundException
     */
    @PutMapping(
            value = "/{customerId}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public Customer updateCustomerById(@PathVariable(name = "customerId") Long customerId, @RequestBody Customer customer) throws CustomerNotFoundException {
        Customer c = this.repository.findOne(customerId);
        if (c == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        c.setFirstName(customer.getFirstName());
        c.setLastName(customer.getLastName());
        c.setAge(customer.getAge());
        return this.repository.save(c);
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
        return this.repository.findAllByAge(age);
    }
}
