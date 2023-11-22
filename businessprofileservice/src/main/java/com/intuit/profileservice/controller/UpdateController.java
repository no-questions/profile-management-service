package com.intuit.profileservice.controller;

import com.intuit.profileservice.dto.UpdateProfileRequestDto;
import com.intuit.profileservice.dto.UpdateProfileResponseDto;
import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.service.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling profile update requests.
 * Annotating a class with {@code @RestController} indicates that this class is a Spring MVC controller
 * where request handling methods are automatically mapped to the corresponding HTTP endpoints.
 * The {@code @AllArgsConstructor} annotation generates a constructor with all fields.
 */
@RestController
@AllArgsConstructor
public class UpdateController {

    // Logger for capturing and logging information
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Autowired services and transformers needed for profile update
    private final ValidateProfileService validateProfileService;
    private final ProfileService profileService;
    private final ErrorCodesService errorCodesService;
    private final RateChecker rateChecker;
    private final HandlingService handlingService;
    private final UpdateProfileService updateProfileService;

    /**
     * Handles HTTP POST requests to update a profile.
     *
     * @param requestDto The request body containing profile update information.
     * @return ResponseEntity containing the response for the profile update request.
     */
    @CrossOrigin
    @PostMapping("/update/profile")
    public ResponseEntity<UpdateProfileResponseDto> updateProfile(@Valid @RequestBody UpdateProfileRequestDto requestDto) {
        logger.info("/update/profile inside updateProfile entry {}", requestDto);

        // Initializing the response object
        UpdateProfileResponseDto res;

        // Checking if the update rate limit is exceeded
        if (rateChecker.getUpdateRate(requestDto.getCustomerId(), "UPDATE", false)) {
            handlingService.handleRateLimitExceeded();
        }
        // Checking for validation failure
        else if (errorCodesService.checkForFailure(validateProfileService.validateProfile(requestDto))) {
            handlingService.handleValidationFailure();
        }

        // Converting DTO to entity model
        Profile profile = updateProfileService.updateProfile(requestDto);

        // Saving the profile
        profileService.saveProfile(profile);

        // Setting up the success response
        res = buildSuccessResponse(profile);

        logger.info("/update/profile inside updateProfile exit {}", res);

        // Updating the rate limit after a successful update
        rateChecker.getUpdateRate(requestDto.getCustomerId(), "UPDATE", true);

        // Returning the response with HTTP status ACCEPTED (202)
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    /**
     * Builds a success response DTO based on the updated profile.
     *
     * @param profile The updated profile.
     * @return The success response DTO.
     */
    private UpdateProfileResponseDto buildSuccessResponse(Profile profile) {
        UpdateProfileResponseDto res = new UpdateProfileResponseDto();
        res.setResCode("200");
        res.setResMessage("Successful");
        res.setCustomerId(profile.getId().toString());
        return res;
    }
}
