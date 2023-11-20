package com.intuit.profileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix
public class BusinessProfileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessProfileServiceApplication.class, args);
    }

}