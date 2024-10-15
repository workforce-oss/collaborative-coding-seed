package com.testbed.accountapi.integration.customerapi;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {
        "customer.service.name=customer-service",
        "customer.service.url=http://localhost:10001"
})
@WireMockTest(httpPort = 10001)
class CustomerApiClientTest {

    @Autowired
    private CustomerApiClient customerApiClient;


    @Test
    void getCustomer() {
        Customer customer = new Customer();
        customer.setId("1");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("test@test.com");

        stubFor(get("/customers/1").willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{\"id\":\"1\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"test@test.com\"}")));

        Customer customerResponse = customerApiClient.getCustomer("1");

        assertEquals(customer.getId(), customerResponse.getId());
        assertEquals(customer.getFirstName(), customerResponse.getFirstName());
        assertEquals(customer.getLastName(), customerResponse.getLastName());
        assertEquals(customer.getEmail(), customerResponse.getEmail());

        verify(getRequestedFor(urlEqualTo("/customers/1")));

        // now test failure

        stubFor(get("/customers/2").willReturn(aResponse()
                .withStatus(404)));

        try {
            customerApiClient.getCustomer("2");
        } catch (Exception e) {
            assertEquals("[404 Not Found] during [GET] to [http://localhost:10001/customers/2] [CustomerApiClient#getCustomer(String)]: []", e.getMessage());
        }

        verify(getRequestedFor(urlEqualTo("/customers/2")));
    }
}