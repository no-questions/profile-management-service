package com.intuit.profileservice.controller;

import com.intuit.profileservice.dto.BaseResponse;
import com.intuit.profileservice.dto.ProfileRequestDto;
import com.intuit.profileservice.dto.CreateProfileResponseDto;
import com.intuit.profileservice.exceptions.ApplicationException;
import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.service.ErrorCodesServiceImpl;
import com.intuit.profileservice.service.ProfileService;
import com.intuit.profileservice.service.ValidateProfileService;
import com.intuit.profileservice.transformer.CreateProfileTransformer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequiredArgsConstructor
public class CreateController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProfileService ps;

    private final ValidateProfileService validateProfileService;

    private final CreateProfileTransformer createProfileTransformer;

    private final ErrorCodesServiceImpl errorCodesServiceImpl;

    @PostMapping("/create/profile")
    public ResponseEntity<CreateProfileResponseDto> createProfile(@Valid @RequestBody ProfileRequestDto requestDto) {
        logger.info("/create/profile inside createProfile entry {}", requestDto);
        CreateProfileResponseDto res = new CreateProfileResponseDto();
        BaseResponse internalResp = validateProfileService.preRequestValidations(requestDto);
        if(!internalResp.getResCode().equalsIgnoreCase("00")) {
            throw new ApplicationException("DC","Duplicate Legal Name");
        }
        else if (errorCodesServiceImpl.checkForFailure(validateProfileService.validateProfile(requestDto))) {
            throw new ApplicationException("FE","Failure occured");
        }
        Profile profile = createProfileTransformer.convertDtoToModel(requestDto);
        ps.saveProfile(profile);
        res.setResCode("200");
        res.setResMessage("Succesfull");
        res.setCustomerId(profile.getId().toString());
        logger.info("/create/profile inside createProfile exit {}", res);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

}
