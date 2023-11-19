package com.intuit.cacheservice.config;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableHystrix
public class HttpConfig {

    @Bean
    RestTemplate getRestController() {
        return new RestTemplate();
    }
}
