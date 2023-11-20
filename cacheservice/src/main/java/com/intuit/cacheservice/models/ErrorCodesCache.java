package com.intuit.cacheservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Builder
@RedisHash
public class ErrorCodesCache implements Serializable {
    @Id
    @Indexed
    private String errorcode;
    private String errormessage;
    @Indexed
    private Boolean isfailure;
    @Indexed
    private Boolean isretryeligible;
}
