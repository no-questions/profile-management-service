package com.intuit.cacheservice.repository;

import com.intuit.cacheservice.models.Ratelimiter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RateLimiterRepository extends CrudRepository<Ratelimiter, Long> {

    List<Ratelimiter> findByCustomeridAndAction(String customerid, String action);

    List<Ratelimiter> findByCustomerid(String customerid);
}
