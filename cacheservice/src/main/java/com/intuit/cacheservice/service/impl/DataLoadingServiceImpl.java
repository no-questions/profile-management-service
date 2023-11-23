package com.intuit.cacheservice.service.impl;

import com.intuit.cacheservice.dto.ErrorCodeDto;
import com.intuit.cacheservice.dto.FetchErrorCodeDBResp;
import com.intuit.cacheservice.models.ErrorCodesCache;
import com.intuit.cacheservice.service.DataLoadingService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataLoadingServiceImpl implements DataLoadingService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RestTemplate restTemplate;
    @Value("${intuit.profileservice.getallerrorcodes}")
    private String getAllErrorCodesURI;

    public static List<ErrorCodesCache> convertList(List<ErrorCodeDto> sourceList) {
        return sourceList.stream()
                .map(sourceObject -> {
                    return ErrorCodesCache.builder().isfailure(sourceObject.getIsfailure())
                            .isretryeligible(sourceObject.getIsretryeligible()).errorcode(sourceObject.getErrorcode())
                            .errormessage(sourceObject.getErrormessage()).build();
                })
                .collect(Collectors.toList());
    }

    @HystrixCommand(fallbackMethod = "dataToBeLoadedFromInMemory", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    @Override
    public List<ErrorCodesCache> loadErrorCodeData() {
        logger.info("retrieving data using loadErrorCodeData");
        ResponseEntity<FetchErrorCodeDBResp> response = restTemplate
                .getForEntity(getAllErrorCodesURI, FetchErrorCodeDBResp.class);
        return convertList(response.getBody().getErrorCodes());
    }

    private List<ErrorCodesCache> dataToBeLoadedFromInMemory() {
        logger.info("Entering fallback method: dataToBeLoadedFromInMemory");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return List.of(
                ErrorCodesCache.builder().errorcode("00").errormessage("Success").isfailure(false).isretryeligible(false).build(),
                ErrorCodesCache.builder().errorcode("1001").errormessage("Internal Server Error").isfailure(true).isretryeligible(true).build(),
                ErrorCodesCache.builder().errorcode("1002").errormessage("Service Unavailable").isfailure(true).isretryeligible(true).build(),
                ErrorCodesCache.builder().errorcode("1003").errormessage("Database Error").isfailure(true).isretryeligible(true).build(),
                ErrorCodesCache.builder().errorcode("1004").errormessage("Network Timeout").isfailure(true).isretryeligible(true).build(),
                ErrorCodesCache.builder().errorcode("1005").errormessage("Unexpected Error").isfailure(true).isretryeligible(true).build(),
                ErrorCodesCache.builder().errorcode("2001").errormessage("Invalid Input").isfailure(true).isretryeligible(false).build(),
                ErrorCodesCache.builder().errorcode("2002").errormessage("Missing Parameter").isfailure(true).isretryeligible(false).build(),
                ErrorCodesCache.builder().errorcode("2003").errormessage("Invalid Token").isfailure(true).isretryeligible(false).build(),
                ErrorCodesCache.builder().errorcode("2004").errormessage("Access Denied").isfailure(true).isretryeligible(false).build(),
                ErrorCodesCache.builder().errorcode("2005").errormessage("Validation Error").isfailure(true).isretryeligible(false).build(),
                ErrorCodesCache.builder().errorcode("2006").errormessage("Duplicate Entry").isfailure(true).isretryeligible(false).build(),
                ErrorCodesCache.builder().errorcode("3001").errormessage("Customer Not Found (Retry Eligible)").isfailure(false).isretryeligible(true).build(),
                ErrorCodesCache.builder().errorcode("3002").errormessage("Transaction Failed (Retry Eligible)").isfailure(false).isretryeligible(true).build(),
                ErrorCodesCache.builder().errorcode("3003").errormessage("Payment Timeout (Retry Eligible)").isfailure(false).isretryeligible(true).build(),
                ErrorCodesCache.builder().errorcode("4001").errormessage("Not Found").isfailure(false).isretryeligible(false).build(),
                ErrorCodesCache.builder().errorcode("4002").errormessage("Resource Unavailable").isfailure(false).isretryeligible(false).build(),
                ErrorCodesCache.builder().errorcode("4003").errormessage("Unauthorized Access").isfailure(false).isretryeligible(false).build(),
                ErrorCodesCache.builder().errorcode("4004").errormessage("Invalid Request").isfailure(false).isretryeligible(false).build(),
                ErrorCodesCache.builder().errorcode("4005").errormessage("Authentication Failed").isfailure(false).isretryeligible(false).build(),
                ErrorCodesCache.builder().errorcode("404").errormessage("No data found").isfailure(true).isretryeligible(false).build(),
                ErrorCodesCache.builder().errorcode("BR").errormessage("Bad Request").isfailure(true).isretryeligible(false).build(),
                ErrorCodesCache.builder().errorcode("DC").errormessage("Duplicate Legal Name").isfailure(true).isretryeligible(false).build(),
                ErrorCodesCache.builder().errorcode("DNE").errormessage("Product not subscribed").isfailure(false).isretryeligible(false).build(),
                ErrorCodesCache.builder().errorcode("FE").errormessage("Failed").isfailure(true).isretryeligible(false).build(),
                ErrorCodesCache.builder().errorcode("LCC").errormessage("Legal Name cannot be changed").isfailure(false).isretryeligible(false).build(),
                ErrorCodesCache.builder().errorcode("RLE").errormessage("Rate limit exceeded").isfailure(true).isretryeligible(true).build()
        );
    }
}
