package com.intuit.cacheservice.controller;

import com.intuit.cacheservice.models.ErrorCodesCache;
import com.intuit.cacheservice.repository.ErrorCodesCacheRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ErrorcodeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ErrorCodesCacheRepository errorCodesCacheRepo;


    @PostMapping("/check/failures")
    public ResponseEntity<Boolean> checkFailures(@RequestBody List<String> errorCodes) {
        logger.info("/check/failures checkFailures entry {}", errorCodes);
        Boolean checkFailures = false;
        List<ErrorCodesCache> notFailureRescodeList = errorCodesCacheRepo.findByIsfailure(false);
        Map<String, ErrorCodesCache> notFailureRescode = new HashMap<>();
        notFailureRescodeList.forEach(errorcode -> notFailureRescode.put(errorcode.getErrorcode(), errorcode));
        for (String errorCode : errorCodes) {
            if (!notFailureRescode.containsKey(errorCode)) {
                checkFailures = true;
            }
        }
        logger.info("/check/failures checkFailures exit {}", checkFailures);
        return new ResponseEntity<>(checkFailures, HttpStatusCode.valueOf(200));
    }
}
