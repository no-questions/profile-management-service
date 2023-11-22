package com.intuit.profileservice.controller;

import com.intuit.profileservice.dto.CreateProfileResponseDto;
import com.intuit.profileservice.dto.ProfileRequestDto;
import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.service.HandlingService;
import com.intuit.profileservice.service.ProfileService;
import com.intuit.profileservice.service.ValidateProfileService;
import com.intuit.profileservice.service.impl.ErrorCodesServiceImpl;
import com.intuit.profileservice.transformer.CreateProfileTransformer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.intuit.profileservice.util.Constants.CREATE_CONTROLLER_PATH;
import static com.intuit.profileservice.util.Constants.CREATE_PROFILE_ENDPOINT;


/**
 * Controller class for handling profile creation requests.
 * Annotating a class with {@code @RestController} indicates that this class is a Spring MVC controller
 * where request handling methods are automatically mapped to the corresponding HTTP endpoints.
 * The {@code @Validated} annotation is used to enable validation on the controller level.
 * The {@code @RequiredArgsConstructor} annotation generates a constructor with required fields.
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(CREATE_CONTROLLER_PATH)
public class CreateController {

    // Logger for capturing and logging information
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Autowired services and transformers needed for profile creation
    private final ProfileService profileService;
    private final ValidateProfileService validateProfileService;
    private final CreateProfileTransformer createProfileTransformer;
    private final ErrorCodesServiceImpl errorCodesServiceImpl;
    private final HandlingService handlingService;

    /**
     * Handles HTTP POST requests to create a new profile.
     *
     * @param requestDto The request body containing profile information.
     * @return ResponseEntity containing the response for the profile creation request.
     */
    @CrossOrigin
    @PostMapping(CREATE_PROFILE_ENDPOINT)
    public ResponseEntity<CreateProfileResponseDto> createProfile(@Valid @RequestBody ProfileRequestDto requestDto) {
        logger.info("{} inside createProfile entry {}", CREATE_PROFILE_ENDPOINT, requestDto);

        // Performing pre-request validations
        boolean internalResp = validateProfileService.preRequestCreationValidations(requestDto);

        // Handling duplicate legal name scenario
        if (!internalResp) {
            handlingService.handleDuplicateLegalName();
        }
        // Handling other validation failures
        else if (errorCodesServiceImpl.checkForFailure(validateProfileService.validateProfile(requestDto))) {
            handlingService.handleValidationFailure();
        }

        // Converting DTO to entity model
        Profile profile = createProfileTransformer.convertDtoToModel(requestDto);

        // Saving the profile
        profileService.saveProfile(profile);

        // Building the success response
        CreateProfileResponseDto res = buildSuccessResponse(profile);

        logger.info("{} inside createProfile exit {}", CREATE_PROFILE_ENDPOINT, res);

        // Returning the response with HTTP status ACCEPTED (202)
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    /**
     * Builds a success response DTO based on the created profile.
     *
     * @param profile The created profile.
     * @return The success response DTO.
     */
    private CreateProfileResponseDto buildSuccessResponse(Profile profile) {
        CreateProfileResponseDto res = new CreateProfileResponseDto();
        res.setResCode("200");
        res.setResMessage("Successful");
        res.setCustomerId(profile.getId().toString());
        return res;
    }
}
