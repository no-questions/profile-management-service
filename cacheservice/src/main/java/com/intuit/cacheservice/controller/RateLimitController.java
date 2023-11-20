package com.intuit.cacheservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.cacheservice.models.Ratelimiter;
import com.intuit.cacheservice.repository.RateLimiterRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RateLimitController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    
    private final RateLimiterRepository rateLimiterRepository;

    @GetMapping("/get/rate")
    public ResponseEntity<Boolean> checkRateLimit(@RequestParam("id") String customerId, @RequestParam("action") String action) {
        try {
            List<Ratelimiter> noOfAttempts = rateLimiterRepository.findByCustomeridAndAction(customerId,action);
            return ResponseEntity.ok((noOfAttempts!=null&&noOfAttempts.size()<2)? false:true);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(false);
        }
    }

    @PostMapping("/set/rate")
    public ResponseEntity<Boolean> increaseRateLimit(@RequestParam("id") String customerId, @RequestParam("action") String action) {
        try{
            Ratelimiter rl = Ratelimiter.builder().action(action).customerid(customerId).build();
            rateLimiterRepository.save(rl);
            return ResponseEntity.ok(true);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Boolean.valueOf(false));
        }
            
    }
}
