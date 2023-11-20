package com.intuit.profileservice.controller;

import com.intuit.profileservice.dto.UpdateProfileRequestDto;
import com.intuit.profileservice.dto.UpdateProfileResponseDto;
import com.intuit.profileservice.exceptions.ApplicationException;
import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.service.ErrorCodesServiceImpl;
import com.intuit.profileservice.service.ProfileService;
import com.intuit.profileservice.service.RateChecker;
import com.intuit.profileservice.service.ValidateProfileService;
import com.intuit.profileservice.transformer.UpdateProfileTransformer;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UpdateController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UpdateProfileTransformer updateProfileTransformer;
    private final ValidateProfileService validateProfileService;
    private final ProfileService ps;
    private final ErrorCodesServiceImpl errorCodesServiceImpl;
    private final RateChecker rateChecker;

    @PostMapping("/update/profile")
    public ResponseEntity<UpdateProfileResponseDto> updateProfile(@Valid @RequestBody UpdateProfileRequestDto requestDto) {
        logger.info("/update/profile inside updateProfile entry {}", requestDto);
        UpdateProfileResponseDto res = new UpdateProfileResponseDto();
        if(rateChecker.getUpdateRate(requestDto.getCustomerId(), "UPDATE", false))
            throw new ApplicationException("RLE","Rate Limit exceeded");
        else if (errorCodesServiceImpl.checkForFailure(validateProfileService.validateProfile(requestDto))) {
            throw new ApplicationException("FE","Failure occured");
        }
        Profile profile = updateProfileTransformer.convertDtoToModel(requestDto);
        ps.saveProfile(profile);
        res.setResCode("200");
        res.setResMes("Succesfull");
        logger.info("/update/profile inside updateProfile exit {}", res);
        rateChecker.getUpdateRate(requestDto.getCustomerId(), "UPDATE", true);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }
}
