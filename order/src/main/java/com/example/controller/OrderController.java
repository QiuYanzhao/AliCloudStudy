package com.example.controller;

import com.example.api.StockClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope // 动态刷新配置
public class OrderController {

    @Autowired
    private StockClient stockClient;

    // @Value可以获取到nacos配置中心的配置，但是无法动态刷新,需要加上@RefreshScope才可以动态刷新
    @Value("${user.name}")
    private String name;


    @GetMapping("/order")
    public void order() {
        stockClient.stock();
        System.err.println("order");
    }
}
