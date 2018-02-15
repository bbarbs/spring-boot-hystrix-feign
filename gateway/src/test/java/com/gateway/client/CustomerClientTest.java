package com.gateway.client;

import com.gateway.context.TestApplicationContext;
import com.gateway.decoder.GatewayErrorDecoder;
import com.gateway.fallback.CustomerFallbackFactory;
import com.gateway.model.Customer;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.feign.support.SpringMvcContract;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerClientTest extends TestApplicationContext {

    CustomerClient customerClient;

    @Before
    public void setup() {
        customerClient = HystrixFeign.builder()
                .contract(new SpringMvcContract())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .errorDecoder(new GatewayErrorDecoder())
                .target(CustomerClient.class, "http://localhost:8080", new CustomerFallbackFactory());
    }

    @Test
    public void testShouldGetAllCustomers() throws IOException {
        try {
            stubFor(
                    get(urlEqualTo("/customers"))
                            .willReturn(aResponse()
                                    .withStatus(HttpStatus.OK.value())
                            )
            );
            List<Customer> customers = this.customerClient.getAllCustomers();
            assertNotNull(customers);
            System.out.println("Size: " + customers.size() + " " + "Data: " + customers.toString());
        } catch (Exception e) {
            this.verifyException(e.getCause());
        }
    }

    @Test
    public void testShouldAddCustomer() throws IOException {
        try {
            stubFor(
                    post(urlEqualTo("/customers"))
                            .willReturn(aResponse()
                                    .withStatus(HttpStatus.OK.value())
                            )
            );
            Customer c = new Customer();
            c.setFirstName("Test");
            c.setLastName("Test");
            c.setAge(13);
            Customer customer = this.customerClient.addCustomer(c);
            assertNotNull(customer);
            System.out.println("Data: " + customer.toString());
        } catch (Exception e) {
            this.verifyException(e.getCause());
        }
    }

    @Test
    public void testShouldGetCustomerById() throws IOException {
        try {
            stubFor(
                    get(urlEqualTo("/customers/1"))
                            .willReturn(aResponse()
                                    .withStatus(HttpStatus.OK.value())
                            )
            );
            Customer customer = this.customerClient.getCustomerById(1L);
            assertNotNull(customer);
            System.out.println("Data: " + customer.toString());
        } catch (Exception e) {
            this.verifyException(e.getCause());
        }
    }

    @Test
    public void testShouldUpdateCustomerById() throws IOException {
        try {
            stubFor(
                    post(urlEqualTo("/customers/1"))
                            .willReturn(aResponse()
                                    .withStatus(HttpStatus.OK.value())
                            )
            );
            Customer c = new Customer();
            c.setFirstName("Test");
            c.setLastName("Test");
            c.setAge(13);
            Customer customer = this.customerClient.updateCustomerById(1L, c);
            assertNotNull(customer);
            System.out.println("Data: " + customer.toString());
        } catch (Exception e) {
            this.verifyException(e.getCause());
        }
    }

    @Test
    public void testShouldDeleteCustomerById() throws IOException {
        try {
            stubFor(
                    post(urlEqualTo("/customers/1"))
                            .willReturn(aResponse()
                                    .withStatus(HttpStatus.OK.value())
                            )
            );
            this.customerClient.deleteCustomerById(1L);
        } catch (Exception e) {
            this.verifyException(e.getCause());
        }
    }

    @Test
    public void testShouldGetCustomerByAge() throws IOException {
        try {
            stubFor(
                    post(urlEqualTo("/customers/?age=12"))
                            .willReturn(aResponse()
                                    .withStatus(HttpStatus.OK.value())
                            )
            );
            List<Customer> customers = this.customerClient.getCustomersByAge(12);
            assertNotNull(customers);
            System.out.println("Size: " + customers.size() + " " + "Data: " + customers.toString());
        } catch (Exception e) {
            this.verifyException(e.getCause());
        }
    }
}
