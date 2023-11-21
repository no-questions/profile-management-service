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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ErrorCodesServiceImpl implements ErrorCodesService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ErrorCodesRepository errorCodesRepository;
    private final RestTemplate restTemplate;

    @NonNull
    private final Set<String> notFailure = new HashSet<>(Set.of("00", "DNE"));

    @Override
    public List<ErrorCodes> getAll() {
        return errorCodesRepository.findAll();
    }

    @Override
    public ErrorCodes findByErrorCode(String errorCode) {
        return errorCodesRepository.findByErrorcode(errorCode);
    }

    @Override
    // @HystrixCommand(fallbackMethod = "fallbackForFailureIdentification", commandProperties = {
    //         @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    // })
    @Retryable(value = { ApplicationException.class })
    public Boolean checkForFailure(ProfileValidationsResp resp) {
        logMethodEntry("checkForFailure");

        try {
            List<String> errCodeList = new ArrayList<>();
            resp.getRes().forEach(res -> errCodeList.add(res.getResCode()));
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("Content-Type", "application/json");
            HttpEntity<List<String>> request = new HttpEntity<>(errCodeList, headers);
            Boolean result = restTemplate.exchange(
                    "http://localhost:1236/check/failures", HttpMethod.POST, request,
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

    @Override
    public Boolean fallbackForFailureIdentification(ProfileValidationsResp resp) {
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
        logger.debug("Entering {} method", methodName);
    }

    private void logMethodExit(String methodName) {
        logger.debug("Exiting {} method", methodName);
    }

    private void logException(String methodName, Exception e) {
        logger.error("{}: Error occurred during execution: {}", methodName, e.getMessage());
    }
}
