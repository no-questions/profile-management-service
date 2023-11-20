package com.intuit.profileservice.service;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.intuit.profileservice.dto.ErrorCodeDto;
import com.intuit.profileservice.exceptions.ApplicationException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RateCheckerImpl implements RateChecker {

    private final RestTemplate restTemplate;

    @Override
    // @HystrixCommand(fallbackMethod = "fallbackForFailureIdentification", commandProperties = {
    //         @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    // })
    @Retryable(value = { ApplicationException.class })
    public Boolean getUpdateRate(String customerId, String action, boolean increment) {
        HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("Content-Type", "application/json");
            // headers.add("message", errorCode);
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(increment?"http://localhost:1236/set/rate":"http://localhost:1236/get/rate")
        .queryParam("id", customerId).queryParam("action", action);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            // ResponseEntity<ErrorCodeDto> response = restTemplate.getForEntity("http://localhost:1236/get/errordescription", ErrorCodeDto.class, entity);
            ResponseEntity<Boolean> response = restTemplate.exchange(builder.toUriString(),increment?HttpMethod.POST:HttpMethod.GET, entity,Boolean.class);
            return response.getBody();
    }
    
}
