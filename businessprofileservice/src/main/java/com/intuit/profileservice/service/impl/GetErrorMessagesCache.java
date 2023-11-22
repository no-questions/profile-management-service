package com.intuit.profileservice.service.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.intuit.profileservice.dto.ErrorCodeDto;
import com.intuit.profileservice.exceptions.ApplicationException;
import com.intuit.profileservice.service.FallbackService;
import com.intuit.profileservice.service.GetErrorMessages;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.RequiredArgsConstructor;

import static com.intuit.profileservice.util.Constants.*;

@Component
@RequiredArgsConstructor
public class GetErrorMessagesCache implements GetErrorMessages {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RestTemplate restTemplate;
    private final FallbackService fallbackService;

    /**
     * Fetches the error message for a given error code from an external service.
     *
     * @param errorCode The error code for which the error message is requested.
     * @return The error message corresponding to the error code.
     * @throws ApplicationException if an application-level exception occurs during the process.
     */
    @Override
    @HystrixCommand(fallbackMethod = "fallbackForErrorMessageDB", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    })
//    @Retryable(value = {ApplicationException.class})
    public String fetchErrorMessage(String errorCode) {
        logMethodEntry("fetchErrorMessage");

        try {
            Thread.sleep(2000);
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("Content-Type", "application/json");
            headers.add("message", errorCode);

            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:1236/get/errordescription")
                    .queryParam("message", errorCode);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<ErrorCodeDto> response = restTemplate.getForEntity(builder.toUriString(), ErrorCodeDto.class, entity);
            ErrorCodeDto res = response.getBody();

            // Check if the response is not null, otherwise fallback to the fallback method
            String errorMessage = (res != null) ? res.getErrormessage() : fallbackForErrorMessageDB(errorCode);

            logMethodExit("fetchErrorMessage");
            return errorMessage;
        } catch (RestClientResponseException e) {
            // Log the exception and fallback to the fallback method
            logException("fetchErrorMessage", e);
            return fallbackForErrorMessageDB(errorCode);
        } catch (Exception e) {
            // Log the exception and throw an application-level exception
            logException("fetchErrorMessage", e);
            throw new ApplicationException("FE", e.getMessage());
        }
    }

    /**
     * Fallback method for retrieving error messages.
     *
     * @param errorCode The error code for which the error message is requested.
     * @return The fallback error message.
     */

    @HystrixCommand(fallbackMethod = "fallbackForErrorMessageDefault", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    })
    private String fallbackForErrorMessageDB(String errorCode) {
        logMethodEntry("fallbackForErrorMessage");
        // Call the fallback service to retrieve the fallback error message
        String errorMessage = fallbackService.fallbackForErrorMessage(errorCode);
        logMethodExit("fallbackForErrorMessage");
        return errorMessage;
    }

    private String fallbackForErrorMessageDefault(String errorCode) {
        logMethodEntry("fallbackForErrorMessageDefault");
        // Call the fallback service to retrieve the fallback error message
        return errorCode.equalsIgnoreCase(SUCCESS_RESCODE)?SUCCESS_RESCODE_DEFAULT_MSG:FAILURE_RESCODE_DEFAULT_MSG;
    }

    private void logMethodEntry(String methodName) {
        logger.info("Entering {} method", methodName);
    }

    private void logMethodExit(String methodName) {
        logger.info("Exiting {} method", methodName);
    }

    private void logException(String methodName, Exception e) {
        logger.error("{}: Error occurred during execution: {}", methodName, e.getMessage());
    }
}

