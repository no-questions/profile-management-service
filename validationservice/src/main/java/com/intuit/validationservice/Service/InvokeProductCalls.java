package com.intuit.validationservice.Service;

import com.intuit.validationservice.dto.BaseResponse;
import com.intuit.validationservice.dto.CreateValidateDto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class InvokeProductCalls {

    private final Logger logger = LoggerFactory.getLogger(InvokeProductCalls.class);
    private final Map<String,String> mapResCodes;

    private static String selectErrorCode(double probability00) {
        String[] errorCodes = {
                "00", "01", "02", "03", "04", "05",
                "50", "51", "52", "53",
                "61", "62", "63",
                "71", "72",
                "90", "91"
        };
        Random random = new Random();
        double randomValue = random.nextDouble();

        if (randomValue < probability00) {
            // Select "00" based on the specified probability
            return "00";
        } else {
            // Select a random error code from the rest of the list
            int randomIndex = random.nextInt(errorCodes.length - 1) + 1;
            return errorCodes[randomIndex];
        }
    }

    @HystrixCommand(fallbackMethod = "fallBackForFailure")
    public BaseResponse invokeProductA(CreateValidateDto req) {
        BaseResponse baseResponse = new BaseResponse();
//        baseResponse.setResCode(selectErrorCode(100));
        baseResponse.setResCode(mapResCodes.get("A"));
        logResponse("invokeProductA", baseResponse);
        return baseResponse;

    }

    @HystrixCommand(fallbackMethod = "fallBackForFailure")
    public BaseResponse invokeProductB(CreateValidateDto req) {
        BaseResponse baseResponse = new BaseResponse();
//        baseResponse.setResCode(selectErrorCode(100));
        baseResponse.setResCode(mapResCodes.get("B"));
        logResponse("invokeProductA", baseResponse);
        return baseResponse;
    }

    @HystrixCommand(fallbackMethod = "fallBackForFailure")
    public BaseResponse invokeProductC(CreateValidateDto req) {
        BaseResponse baseResponse = new BaseResponse();
//        baseResponse.setResCode(selectErrorCode(100));
        baseResponse.setResCode(mapResCodes.get("C"));
        logResponse("invokeProductC", baseResponse);
        return baseResponse;
    }

    @HystrixCommand(fallbackMethod = "fallBackForFailure")
    public BaseResponse invokeProductD(CreateValidateDto req) {
        BaseResponse baseResponse = new BaseResponse();
//        baseResponse.setResCode(selectErrorCode(100));
        baseResponse.setResCode(mapResCodes.get("D"));
        logResponse("invokeProductD", baseResponse);
        return baseResponse;
    }

    private BaseResponse fallBackForFailure(CreateValidateDto req){
        return new BaseResponse("FE","Failure");
    }

    private void logResponse(String methodName, BaseResponse response) {
        if (response.getResCode().equalsIgnoreCase("00")) {
            logger.info("{} - Success: {}", methodName, response.getResMessage());
        } else {
            logger.error("{} - Failure: {}", methodName, response.getResMessage());
        }
    }
}
