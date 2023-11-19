package com.intuit.cacheservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Builder
@RedisHash
public class ErrorCodesCache implements Serializable {
    @Id
    private String errorcode;
    private String errormessage;
    private Boolean isfailure;
    private Boolean isretryeligible;
}
