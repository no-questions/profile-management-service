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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for handling requests related to error codes from the database.
 * Annotating a class with {@code @RestController} indicates that this class is a Spring MVC controller
 * where request handling methods are automatically mapped to the corresponding HTTP endpoints.
 * The {@code @RequiredArgsConstructor} annotation generates a constructor with required fields.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping
public class DBController {

    // Logger for capturing and logging information
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Autowired service needed for error codes
    private final ErrorCodesService errorCodesService;

    /**
     * Handles HTTP GET requests to retrieve error codes from the database.
     *
     * @return ResponseEntity containing the response for the error codes retrieval request.
     */
    @GetMapping("/get/errorcodes")
    public ResponseEntity<FetchErrorCodeDBResp> getErrorCodes() {
        logger.info("/get/errorcodes inside getErrorCodes entry");

        // Initializing the response object
        FetchErrorCodeDBResp resp = new FetchErrorCodeDBResp();
        resp.setErrorCodes(errorCodesService.convertList(errorCodesService.getAll()));

        logger.info("/get/errorcodes getErrorCodes exit {}", resp);

        // Returning the response with HTTP status OK (200)
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
