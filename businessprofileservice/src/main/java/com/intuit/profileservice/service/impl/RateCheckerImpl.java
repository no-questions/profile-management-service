package com.intuit.profileservice.service.impl;

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

import com.intuit.profileservice.exceptions.ApplicationException;
import com.intuit.profileservice.service.RateChecker;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RateCheckerImpl implements RateChecker {

    // Autowired dependency using Lombok's @RequiredArgsConstructor
    private final RestTemplate restTemplate;

    /**
     * Gets or updates the rate based on the provided parameters.
     *
     * @param customerId The ID of the customer.
     * @param action     The action to be performed.
     * @param increment  A flag indicating whether to increment the rate.
     * @return true if the rate is updated successfully, false otherwise.
     * @throws ApplicationException if an application-level exception occurs during the rate check.
     */
    @Override
    // @HystrixCommand(fallbackMethod = "fallbackForFailureIdentification", commandProperties = {
    //         @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    // })
    @Retryable(value = { ApplicationException.class })
    public Boolean getUpdateRate(String customerId, String action, boolean increment) {
        // Set up HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Content-Type", "application/json");

        // Build the URI based on the action and increment flag
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(
                        increment ? "http://localhost:1236/set/rate" : "http://localhost:1236/get/rate")
                .queryParam("id", customerId).queryParam("action", action);

        // Create an HTTP entity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make a REST call to get or update the rate
        ResponseEntity<Boolean> response = restTemplate.exchange(
                builder.toUriString(),
                increment ? HttpMethod.POST : HttpMethod.GET,
                entity,
                Boolean.class);

        // Return the response body
        return response.getBody();
    }
}

