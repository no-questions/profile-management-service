package com.intuit.profileservice.service.impl;

import com.intuit.profileservice.dto.BaseResponse;
import com.intuit.profileservice.dto.ProfileRequestDto;
import com.intuit.profileservice.dto.ProfileValidationsResp;
import com.intuit.profileservice.exceptions.ApplicationException;
import com.intuit.profileservice.exceptions.BadRequestException;
import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.service.ValidateProfileService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ValidateProfileServiceImpl implements ValidateProfileService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProfileServiceImpl profileService;

    private final RestTemplate restTemplate;

    @Override
    public BaseResponse preRequestValidations(ProfileRequestDto request) {
        logger.debug("Entering preRequestValidations method");

        try {
            Optional<Profile> profiles = profileService.getProfileByCompanyName(request.getLegalName());
            BaseResponse response = new BaseResponse();
            if (profiles.isPresent()) {
                response.setResCode("DC");
            } else {
                response.setResCode("00");
            }

            logger.debug("preRequestValidations method executed successfully");
            return response;
        } catch (Exception e) {
            logger.error("preRequestValidations: Error occurred during execution: {}", e.getMessage());
            throw e; // Rethrow the exception after logging
        } finally {
            logger.debug("Exiting preRequestValidations method");
        }
    }

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
            ResponseEntity<ProfileValidationsResp> resp = restTemplate.exchange(
                    "http://localhost:1235/validate/profile", HttpMethod.POST, request,
                    ProfileValidationsResp.class);

            logger.debug("validateProfile method executed successfully");
            return resp.getBody();
        } catch (RestClientResponseException e) {
            if (e.getStatusCode().is4xxClientError()) {
                logger.error("validateProfile: Bad Request Exception: {}", e.getMessage());
                throw new BadRequestException("BR", e.getMessage());
            } else {
                logger.error("validateProfile: Application Exception: {}", e.getMessage());
                throw new ApplicationException("FE", e.getMessage());
            }
        } catch (Exception e) {
            logger.error("validateProfile: Error occurred during execution: {}", e.getMessage());
            throw e; // Rethrow the exception after logging
        } finally {
            logger.debug("Exiting validateProfile method");
        }
    }
}
