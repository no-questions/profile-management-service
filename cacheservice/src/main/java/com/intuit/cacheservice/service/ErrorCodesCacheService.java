package com.intuit.cacheservice.service;

import com.intuit.cacheservice.dto.ErrorCodeDto;
import com.intuit.cacheservice.dto.FetchErrorCodeDBResp;
import com.intuit.cacheservice.exceptions.ApplicationException;
import com.intuit.cacheservice.exceptions.BadRequestException;
import com.intuit.cacheservice.models.ErrorCodesCache;
import com.intuit.cacheservice.repository.ErrorCodesCacheRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ErrorCodesCacheService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ErrorCodesCacheRepository errorCodesCacheRepository;

    private final RestTemplate restTemplate;

    public static List<ErrorCodesCache> convertList(List<ErrorCodeDto> sourceList) {
        return sourceList.stream()
                .map(sourceObject -> {
                    return ErrorCodesCache.builder().isfailure(sourceObject.getIsfailure())
                            .isretryeligible(sourceObject.getIsretryeligible()).errorcode(sourceObject.getErrorcode())
                            .errormessage(sourceObject.getErrormessage()).build();
                })
                .collect(Collectors.toList());
    }

    public String getErrorDescription(String errorCode) {
        ErrorCodesCache errorDescription = errorCodesCacheRepository.findByErrorcode(errorCode);
        return errorDescription != null ? errorDescription.getErrormessage() : null;
    }

    @PostConstruct
    // @HystrixCommand(fallbackMethod = "fallbackForErrorMessage", commandProperties = {
    //         @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    // })
    @Retryable(value = { ApplicationException.class })
    void refreshCache() {
        logger.info("Entering refreshCache method");

        try {
            errorCodesCacheRepository.deleteAll();
            ResponseEntity<FetchErrorCodeDBResp> response = restTemplate
                    .getForEntity("http://localhost:1234/get/errorcodes", FetchErrorCodeDBResp.class);
            FetchErrorCodeDBResp res = response.getBody();
            errorCodesCacheRepository.saveAll(convertList(res.getErrorCodes()));
            logger.info("Cache refreshed successfully");
        } catch (RestClientResponseException e) {
            if (e.getStatusCode().is4xxClientError()) {
                logger.error(e.getMessage());
                throw new BadRequestException("BR", e.getMessage());
            } else {
                logger.error(e.getMessage());
                throw new ApplicationException("FE", e.getMessage());
            }
        } finally {
            logger.info("Exiting refreshCache method");
        }
    }
}
