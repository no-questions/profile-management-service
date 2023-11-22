package com.intuit.cacheservice.models;

import com.intuit.cacheservice.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@AllArgsConstructor
@Getter
@Builder
@RedisHash
public class Ratelimiter {
    // private String count;
    @TimeToLive
    @Builder.Default
    private final Long milliSecs = Constants.milliSecsInOneDay;
    @Id
    private Long id;
    @Indexed
    private String customerid;
    @Indexed
    private String action;
}
