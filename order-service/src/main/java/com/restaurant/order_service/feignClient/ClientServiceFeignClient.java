package com.restaurant.order_service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.restaurant.order_service.dto.ClientResponse;

@FeignClient(name = "client-service", url = "http://localhost:8083/api/clients")
public interface ClientServiceFeignClient {
    
    @GetMapping("/{id}")
    ClientResponse getClientById(@PathVariable("id") int id);
}
