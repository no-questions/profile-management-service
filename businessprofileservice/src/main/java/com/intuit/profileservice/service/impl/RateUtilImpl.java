package com.intuit.profileservice.service.impl;

import com.intuit.profileservice.exceptions.ApplicationException;
import com.intuit.profileservice.service.RateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class RateUtilImpl implements RateUtil {

    private final RestTemplate restTemplate;

    @Value("${intuit.rateupdate.url}")
    private String rateUpdateURL;
    @Value("${intuit.ratecheck.url}")
    private String rateCheckURL;


    /**
     * checks the ratelimit based on the provided parameters.
     *
     * @param customerId The ID of the customer.
     * @param action     The action to be performed.
     * @return true if the rate is updated successfully, false otherwise.
     * @throws ApplicationException if an application-level exception occurs during the rate check.
     */
    @Override
    // @HystrixCommand(fallbackMethod = "fallbackForFailureIdentification", commandProperties = {
    //         @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    // })
    public Boolean getRate(String customerId, String action) {
        // Set up HTTP headers
        HttpHeaders headers = getHttpHeaders();

        // Build the URI based on the action and increment flag
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(
                        rateCheckURL)
                .queryParam("id", customerId).queryParam("action", action);

        // Create an HTTP entity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make a REST call to get or update the rate
        ResponseEntity<Boolean> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                Boolean.class);

        // Return the response body
        return response.getBody();
    }

    /**
     * increments the reate based on the provided parameters.
     *
     * @param customerId The ID of the customer.
     * @param action     The action to be performed.
     * @return true if the rate is updated successfully, false otherwise.
     * @throws ApplicationException if an application-level exception occurs during the rate check.
     */
    @Override
    // @HystrixCommand(fallbackMethod = "fallbackForFailureIdentification", commandProperties = {
    //         @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    // })
    public Boolean updateRate(String customerId, String action) {
        // Set up HTTP headers
        HttpHeaders headers = getHttpHeaders();

        // Build the URI based on the action and increment flag
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(rateUpdateURL)
                .queryParam("id", customerId).queryParam("action", action);

        // Create an HTTP entity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make a REST call to get or update the rate
        ResponseEntity<Boolean> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                entity,
                Boolean.class);

        // Return the response body
        return response.getBody();
    }

    private HttpHeaders getHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Content-Type", "application/json");
        return headers;
    }
}

