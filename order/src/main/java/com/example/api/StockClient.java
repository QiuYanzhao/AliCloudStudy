package com.example.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "stock-service", path = "/stock")
public interface StockClient {

    @GetMapping
    void stock();
}
