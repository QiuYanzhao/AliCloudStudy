package com.example;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.*;

@SpringBootTest
public class Test {

    private ExecutorService pool = Executors.newFixedThreadPool(5);

    @org.junit.Test
    public void test() throws Exception {

    }
}
