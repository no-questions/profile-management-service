package com.intuit.cacheservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
@EnableCaching
public class CacheserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheserviceApplication.class, args);
    }

}
