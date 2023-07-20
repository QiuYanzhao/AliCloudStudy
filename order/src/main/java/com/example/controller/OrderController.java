package com.example.controller;

import com.example.api.StockClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {

    @Autowired
    private StockClient stockClient;


    @GetMapping("/order")
    public void order() {
        stockClient.stock();
        System.err.println("order");
    }
}
