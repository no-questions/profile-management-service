package com.intuit.validationservice.Service;

import com.intuit.validationservice.dto.BaseResponse;
import com.intuit.validationservice.dto.CreateValidateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class InvokeProductCalls {

    private final Logger logger = LoggerFactory.getLogger(InvokeProductCalls.class);

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

    public BaseResponse invokeProductA(CreateValidateDto req) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setResCode(selectErrorCode(100));
        logResponse("invokeProductA", baseResponse);
        return baseResponse;
    }

    public BaseResponse invokeProductB(CreateValidateDto req) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setResCode(selectErrorCode(90));
        logResponse("invokeProductB", baseResponse);
        return baseResponse;
    }

    public BaseResponse invokeProductC(CreateValidateDto req) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setResCode(selectErrorCode(80));
        logResponse("invokeProductC", baseResponse);
        return baseResponse;
    }

    public BaseResponse invokeProductD(CreateValidateDto req) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setResCode(selectErrorCode(100));
        logResponse("invokeProductD", baseResponse);
        return baseResponse;
    }

    private void logResponse(String methodName, BaseResponse response) {
        if (response.getResCode().equalsIgnoreCase("00")) {
            logger.info("{} - Success: {}", methodName, response.getResMessage());
        } else {
            logger.error("{} - Failure: {}", methodName, response.getResMessage());
        }
    }
}
