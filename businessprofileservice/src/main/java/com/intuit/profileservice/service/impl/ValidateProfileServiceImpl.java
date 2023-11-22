package com.intuit.profileservice.service.impl;

import com.intuit.profileservice.dto.ProfileRequestDto;
import com.intuit.profileservice.dto.ProfileValidationsResp;
import com.intuit.profileservice.exceptions.ApplicationException;
import com.intuit.profileservice.exceptions.BadRequestException;
import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.service.HandlingService;
import com.intuit.profileservice.service.ValidateProfileService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Optional;

import static com.intuit.profileservice.util.Constants.RESCODE_BADREQUEST;
import static com.intuit.profileservice.util.Constants.RESCODE_VALIDATIONFAILURE;

@Service
@RequiredArgsConstructor
public class ValidateProfileServiceImpl implements ValidateProfileService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ProfileServiceImpl profileService;
    private final RestTemplate restTemplate;
    private final HandlingService handlingService;
    @Value("${intuit.validateprofile.url}")
    private String validateProfileUrl;

    /**
     * Performs pre-request validations for profile creation.
     *
     * @param request The profile creation request.
     * @return true if the validation passes, false otherwise.
     * @throws Exception if an error occurs during the validation process.
     */
    @Override
    public boolean preRequestCreationValidations(ProfileRequestDto request) {
        logger.debug("Entering preRequestValidations method");
        boolean check = true;
        try {
            Optional<Profile> profiles = profileService.getProfileByCompanyName(request.getLegalName());
            // If a profile with the same legal name exists, set check to false
            if (profiles.isPresent())
                check = false;
            logger.debug("preRequestValidations method executed successfully");
            return check;
        } catch (Exception e) {
            logger.error("preRequestValidations: Error occurred during execution: {}", e.getMessage());
            throw e;
        } finally {
            logger.debug("Exiting preRequestValidations method");
        }
    }

    /**
     * Validates a profile using an external service through REST.
     *
     * @param requestDto The profile validation request.
     * @return The response from the profile validation.
     * @throws BadRequestException  if a 4xx client error occurs during the REST call.
     * @throws ApplicationException if an application-level exception occurs during the REST call.
     * @throws Exception            if an error occurs during the validation process.
     */
    @Override
    @HystrixCommand(fallbackMethod = "fallbackForErrorMessage", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    })
    @Retryable(value = {ApplicationException.class})
    public ProfileValidationsResp validateProfile(ProfileRequestDto requestDto) {
        logger.debug("Entering validateProfile method");

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("Content-Type", "application/json");
            HttpEntity<ProfileRequestDto> request = new HttpEntity<>(requestDto, headers);

            // Make a REST call to the external profile validation service
            ResponseEntity<ProfileValidationsResp> resp = restTemplate.exchange(
                    validateProfileUrl, HttpMethod.POST, request,
                    ProfileValidationsResp.class);

            logger.debug("validateProfile method executed successfully");
            return resp.getBody();
        } catch (RestClientResponseException e) {
            // Handle 4xx client errors separately
            if (e.getStatusCode().is4xxClientError()) {
                logger.error("validateProfile: Bad Request Exception: {}", e.getMessage());
                throw new BadRequestException(RESCODE_BADREQUEST, e.getMessage());
            } else {
                // Handle other exceptions as application-level exceptions
                logger.error("validateProfile: Application Exception: {}", e.getMessage());
                throw new ApplicationException(RESCODE_VALIDATIONFAILURE, e.getMessage());
            }
        } catch (Exception e) {
            // Handle other exceptions
            logger.error("validateProfile: Error occurred during execution: {}", e.getMessage());
            throw e;
        } finally {
            logger.debug("Exiting validateProfile method");
        }
    }

    private ProfileValidationsResp fallbackForErrorMessage(ProfileRequestDto request) {
        logger.info("Entering Fallback method: fallbackForErrorMessage {}", request);
        handlingService.handleValidationFailure();
        return null;
    }
}
