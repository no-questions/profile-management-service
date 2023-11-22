package com.intuit.profileservice.service.impl;

import com.intuit.profileservice.dto.ErrorCodeDto;
import com.intuit.profileservice.dto.ProfileValidationsResp;
import com.intuit.profileservice.exceptions.ApplicationException;
import com.intuit.profileservice.exceptions.BadRequestException;
import com.intuit.profileservice.models.ErrorCodes;
import com.intuit.profileservice.repository.ErrorCodesRepository;
import com.intuit.profileservice.service.ErrorCodesService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ErrorCodesServiceImpl implements ErrorCodesService {

    // Autowired dependencies using Lombok's @RequiredArgsConstructor
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ErrorCodesRepository errorCodesRepository;
    private final RestTemplate restTemplate;

    // Set of error codes that are not considered failures
    @NonNull
    private final Set<String> notFailure = new HashSet<>(Set.of("00", "DNE"));

    @Value("${intuit.checkfailure.url}")
    private String checkFailureUrl;

    /**
     * Retrieves all error codes.
     *
     * @return List of all error codes.
     */
    @Override
    public List<ErrorCodes> getAll() {
        return errorCodesRepository.findAll();
    }

    /**
     * Retrieves an error code by its code.
     *
     * @param errorCode The error code to retrieve.
     * @return The error code object if found, otherwise null.
     */
    @Override
    public ErrorCodes findByErrorCode(String errorCode) {
        return errorCodesRepository.findByErrorcode(errorCode);
    }

    /**
     * Checks if any response codes in the ProfileValidationsResp indicate a failure
     * by making a request to an external service.
     *
     * @param resp The ProfileValidationsResp containing response codes.
     * @return true if any response code indicates a failure, false otherwise.
     * @throws BadRequestException  if a 4xx client error occurs during the check.
     * @throws ApplicationException if an error occurs during the check.
     */
    @Override
    @HystrixCommand(fallbackMethod = "fallbackForFailureIdentification", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    })
    public Boolean checkForFailure(ProfileValidationsResp resp) {
        logMethodEntry("checkForFailure");

        try {
//            Thread.sleep(2000);
            List<String> errCodeList = new ArrayList<>();
            resp.getRes().forEach(res -> errCodeList.add(res.getResCode()));
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("Content-Type", "application/json");
            HttpEntity<List<String>> request = new HttpEntity<>(errCodeList, headers);
            Boolean result = restTemplate.exchange(
                    checkFailureUrl, HttpMethod.POST, request,
                    Boolean.class).getBody();
            logMethodExit("checkForFailure");
            return result;
        } catch (RestClientResponseException e) {
            logException("checkForFailure", e);
            if (e.getStatusCode().is4xxClientError()) {
                throw new BadRequestException("BR", e.getMessage());
            } else {
                throw new ApplicationException("FE", e.getMessage());
            }
        } catch (Exception e) {
            logException("checkForFailure", e);
            throw new ApplicationException("FE", e.getMessage());
        }
    }

    /**
     * Fallback method for identifying failures in case of external service failure.
     *
     * @param resp The ProfileValidationsResp containing response codes.
     * @return true if any response code indicates a failure, false otherwise.
     */
    private Boolean fallbackForFailureIdentification(ProfileValidationsResp resp) {
        logMethodEntry("fallbackForFailureIdentification");

        Boolean check = false;
        List<String> errCodeList = new ArrayList<>();
        resp.getRes().forEach(res -> errCodeList.add(res.getResCode()));
        for (String errCode : errCodeList) {
            if (notFailure.contains(errCode))
                return true;
        }

        logMethodExit("fallbackForFailureIdentification");
        return check;
    }

    /**
     * Converts a list of ErrorCodes to a list of ErrorCodeDto objects.
     *
     * @param sourceList List of ErrorCodes to be converted.
     * @return List of ErrorCodeDto objects.
     */
    @Override
    public List<ErrorCodeDto> convertList(List<ErrorCodes> sourceList) {
        return sourceList.stream()
                .map(sourceObject -> {
                    ErrorCodeDto targetObject = new ErrorCodeDto();
                    targetObject.setErrorcode(sourceObject.getErrorcode());
                    targetObject.setErrormessage(sourceObject.getErrormessage());
                    targetObject.setIsfailure(sourceObject.getIsfailure());
                    targetObject.setIsretryeligible(sourceObject.getIsretryeligible());
                    return targetObject;
                })
                .collect(Collectors.toList());
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
