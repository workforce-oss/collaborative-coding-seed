package com.testbed.accountapi.integration.customerapi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${customer.service.name}", url = "${customer.service.url}")
public interface CustomerApiClient {
    @GetMapping("/customers/{id}")
    Customer getCustomer(@PathVariable("id") String id);
}