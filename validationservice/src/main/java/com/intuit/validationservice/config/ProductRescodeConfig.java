package com.intuit.validationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProductRescodeConfig {

    @Bean
    Map<String, String> productResCodes() {
        Map<String, String> mapResCodes = new HashMap<>();
        mapResCodes.put("A","00");
        mapResCodes.put("B","00");
        mapResCodes.put("C","00");
        mapResCodes.put("D","00");
        return mapResCodes;
    }
}
