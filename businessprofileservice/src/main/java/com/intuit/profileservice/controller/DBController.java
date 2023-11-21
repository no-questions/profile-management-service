package com.intuit.profileservice.controller;

import com.intuit.profileservice.dto.ErrorCodeDto;
import com.intuit.profileservice.dto.FetchErrorCodeDBResp;
import com.intuit.profileservice.models.ErrorCodes;
import com.intuit.profileservice.service.ErrorCodesService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DBController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ErrorCodesService errorCodesService;


    @GetMapping("/get/errorcodes")
    public ResponseEntity<FetchErrorCodeDBResp> getErrorCodes() {
        logger.info("/get/errorcodes inside getErrorCodes entry");
        FetchErrorCodeDBResp resp = new FetchErrorCodeDBResp();
        resp.setErrorCodes(errorCodesService.convertList(errorCodesService.getAll()));
        logger.info("/get/errorcodes getErrorCodes exit {}",resp);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
