package com.intuit.cacheservice.controller;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.cacheservice.models.ErrorCodesCache;
import com.intuit.cacheservice.repository.ErrorCodesCacheRepository;

@RestController
@RequiredArgsConstructor
public class FetchController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ErrorCodesCacheRepository errorCodesCacheRepository;

    @GetMapping("/get/errordescription")
    public ResponseEntity<ErrorCodesCache> getErrorDescription(@RequestHeader("message") String message) {
        logger.info("/get/errordescription getErrorDescription entry {}",message);
        return new ResponseEntity<>(errorCodesCacheRepository.findByErrorcode(message), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(errorCodesCacheRepository.findAll(), HttpStatusCode.valueOf(200));
    }
}
