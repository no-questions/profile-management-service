package com.intuit.cacheservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@EnableRetry
public class HttpConfiguration {

    @Value("${intuit.connection.timeout}")
    private Long connectionTimeout;
    @Value("${intuit.read.timeout}")
    private Long readTimeout;


    /**
     * Bean definition for a customized RestTemplate.
     *
     * @param restTemplateBuilder The RestTemplateBuilder for building and customizing RestTemplate.
     * @return A customized RestTemplate instance.
     */
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(connectionTimeout))
                .setReadTimeout(Duration.ofSeconds(readTimeout))
                .build();
    }
}