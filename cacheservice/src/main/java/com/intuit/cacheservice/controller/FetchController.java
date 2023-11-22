package com.intuit.cacheservice.controller;

import com.intuit.cacheservice.models.ErrorCodesCache;
import com.intuit.cacheservice.service.ErrorCodesCacheService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FetchController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ErrorCodesCacheService errorCodesCacheService;

    @GetMapping("/get/errordescription")
    public ResponseEntity<ErrorCodesCache> getErrorDescription(@RequestParam("message") String message) {
        logger.info("/get/errordescription getErrorDescription entry {}", message);
        return new ResponseEntity<>(errorCodesCacheService.getByErrorCode(message), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(errorCodesCacheService.getAllErrorCodes(), HttpStatusCode.valueOf(200));
    }
}
