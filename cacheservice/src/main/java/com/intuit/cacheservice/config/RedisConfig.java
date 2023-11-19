package com.intuit.cacheservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.intuit.cacheservice.models.ErrorCodesCache;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;
    @Value("${spring.data.redis.port}")
    private int redisPort;

    // @Bean
    // public RedisTemplate<String, ErrorCodesCache>
    // redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    // RedisTemplate<String, ErrorCodesCache> redisTemplate = new RedisTemplate<>();
    // redisTemplate.setConnectionFactory(redisConnectionFactory);
    // return redisTemplate;
    // }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisHost);
        configuration.setPort(redisPort);
        return new LettuceConnectionFactory(configuration);
    }

    // @Bean
    // public RedisCacheManager cacheManager(RedisConnectionFactory
    // connectionFactory) {
    // return RedisCacheManager.create(connectionFactory);
    // }

    // @Bean
    // public HashOperations<String, String, ErrorCodesCache>
    // getHashOperations(RedisTemplate<String, ErrorCodesCache> redisTemplate) {
    // return redisTemplate.opsForHash();
    // }
    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

}
