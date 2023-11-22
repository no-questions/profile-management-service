package com.intuit.validationservice.controller;

import com.intuit.validationservice.Service.InvokeProductCalls;
import com.intuit.validationservice.dto.BaseResponse;
import com.intuit.validationservice.dto.CreateValidateDto;
import com.intuit.validationservice.dto.ValidateProfileResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@Validated
@RequiredArgsConstructor
public class ProfileValidationController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final InvokeProductCalls invokeProductCalls;
    private final Map<String,String> mapResCodes;

    @PostMapping("/validate/profile")
    public ResponseEntity<?> validateProfileCreation(@RequestBody CreateValidateDto request) {
        logger.info("/validate/profile validateProfileCreation entry {}", request);
        CompletableFuture<BaseResponse> productA = CompletableFuture.supplyAsync(() -> invokeProductCalls.invokeProductA(request));
        CompletableFuture<BaseResponse> productB = CompletableFuture.supplyAsync(() -> invokeProductCalls.invokeProductB(request));
        CompletableFuture<BaseResponse> productC = CompletableFuture.supplyAsync(() -> invokeProductCalls.invokeProductC(request));
        CompletableFuture<BaseResponse> productD = CompletableFuture.supplyAsync(() -> invokeProductCalls.invokeProductD(request));
        List<BaseResponse> responseList = new ArrayList<>();
        try {
            CompletableFuture<ValidateProfileResponse> result = CompletableFuture.allOf(productA, productB, productC, productD)
                    .thenApply(ignore -> {
                        try {
                            responseList.add(productA.get());
                            responseList.add(productB.get());
                            responseList.add(productC.get());
                            responseList.add(productD.get());
                        } catch (InterruptedException | ExecutionException e) {
                            logger.error(e.getMessage());
                        }
                        return new ValidateProfileResponse(responseList);
                    });
            return ResponseEntity.ok(result.join());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @PutMapping("/set/rescode")
    public ResponseEntity<Boolean> setRescode(String A, String B, String C, String D) {
        mapResCodes.putIfAbsent("A",A);
        mapResCodes.putIfAbsent("B",B);
        mapResCodes.putIfAbsent("C",C);
        mapResCodes.putIfAbsent("D",D);
        return ResponseEntity.ok(true);
    }
}