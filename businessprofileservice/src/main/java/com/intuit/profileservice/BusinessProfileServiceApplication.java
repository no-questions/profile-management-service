package com.intuit.profileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableCircuitBreaker
@EnableRetry
public class BusinessProfileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessProfileServiceApplication.class, args);
    }

}