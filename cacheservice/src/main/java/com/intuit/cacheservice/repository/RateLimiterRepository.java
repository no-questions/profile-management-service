package com.intuit.cacheservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.intuit.cacheservice.models.Ratelimiter;

@Repository
public interface RateLimiterRepository  extends CrudRepository <Ratelimiter, Long> {
    
}
