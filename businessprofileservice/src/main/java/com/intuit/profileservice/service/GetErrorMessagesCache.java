package com.intuit.profileservice.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.intuit.profileservice.dto.ErrorCodeDto;
import com.intuit.profileservice.exceptions.ApplicationException;
import com.intuit.profileservice.exceptions.BadRequestException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetErrorMessagesCache implements GetErrorMessages {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestTemplate restTemplate;

    @NonNull
    private final GetErrorMessagesDB getMessageFromDB = new GetErrorMessagesDB();

    @Override
    @HystrixCommand(fallbackMethod = "fallbackForErrorMessage", commandProperties = {
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
     })
    @Retryable(value = {ApplicationException.class})
    public String fetchErrorMessage(String errorCode) {
        logMethodEntry("fetchErrorMessage");

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("Content-Type", "application/json");
            headers.add("message", errorCode);
            ResponseEntity<ErrorCodeDto> response = restTemplate.getForEntity("http://localhost:1236/get/errorcodes", ErrorCodeDto.class, headers);
            ErrorCodeDto res = response.getBody();
            String errorMessage = (res != null) ? res.getErrormessage() : getMessageFromDB.fetchErrorMessage(errorCode);
            logMethodExit("fetchErrorMessage");
            return errorMessage;
        } catch (RestClientResponseException e) {
            logException("fetchErrorMessage", e);
            if (e.getStatusCode().is4xxClientError()) {
                throw new BadRequestException("BR", e.getMessage());
            } else {
                throw new ApplicationException("FE", e.getMessage());
            }
        } catch (Exception e) {
            logException("fetchErrorMessage", e);
            throw new ApplicationException("FE", e.getMessage());
        }
    }

    public String fallbackForErrorMessage(String errorCode) {
        logMethodEntry("fallbackForErrorMessage");
        String errorMessage = getMessageFromDB.fetchErrorMessage(errorCode);
        logMethodExit("fallbackForErrorMessage");
        return errorMessage;
    }

    private void logMethodEntry(String methodName) {
        logger.debug("Entering {} method", methodName);
    }

    private void logMethodExit(String methodName) {
        logger.debug("Exiting {} method", methodName);
    }

    private void logException(String methodName, Exception e) {
        logger.error("{}: Error occurred during execution: {}", methodName, e.getMessage());
    }
}

