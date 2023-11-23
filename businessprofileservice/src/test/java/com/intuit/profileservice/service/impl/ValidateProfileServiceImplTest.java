package com.intuit.profileservice.service.impl;

import com.intuit.profileservice.dto.BaseResponse;
import com.intuit.profileservice.dto.ProfileRequestDto;
import com.intuit.profileservice.dto.ProfileValidationsResp;
import com.intuit.profileservice.exceptions.ApplicationException;
import com.intuit.profileservice.exceptions.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ValidateProfileServiceImplTest {

    @Mock
    private ProfileServiceImpl profileService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ValidateProfileServiceImpl validateProfileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testPreRequestValidations_ProfileExists() {
//        ProfileRequestDto requestDto = new ProfileRequestDto();
//        requestDto.setLegalName("ExistingCompany");
//
//        when(profileService.getProfileByCompanyName("ExistingCompany")).thenReturn(Optional.of(new Profile()));
//        BaseResponse result = validateProfileService.preRequestCreationValidations(requestDto);
//
//        assertEquals("DC", result.getResCode());
//        verify(profileService, times(1)).getProfileByCompanyName("ExistingCompany");
//    }
//
//    @Test
//    void testPreRequestValidations_ProfileDoesNotExist() {
//        ProfileRequestDto requestDto = new ProfileRequestDto();
//        requestDto.setLegalName("NonExistingCompany");
//
//        when(profileService.getProfileByCompanyName("NonExistingCompany")).thenReturn(Optional.empty());
//        BaseResponse result = validateProfileService.preRequestCreationValidations(requestDto);
//        assertEquals("00", result.getResCode());
//        verify(profileService, times(1)).getProfileByCompanyName("NonExistingCompany");
//    }

    @Test
    void testPreRequestValidations_Exception() {
        ProfileRequestDto requestDto = new ProfileRequestDto();
        requestDto.setLegalName("ExceptionCompany");

        when(profileService.getProfileByCompanyName("ExceptionCompany")).thenThrow(new RuntimeException("Test Exception"));
        assertThrows(RuntimeException.class, () -> validateProfileService.preRequestCreationValidations(requestDto));
        verify(profileService, times(1)).getProfileByCompanyName("ExceptionCompany");
    }

//    @Test
//    void testValidateProfile_Success() {
//        ProfileRequestDto requestDto = new ProfileRequestDto();
//        requestDto.setLegalName("ValidateCompany");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<ProfileRequestDto> request = new HttpEntity<>(requestDto, headers);
//
//        ProfileValidationsResp mockResponse = new ProfileValidationsResp();
//        mockResponse.setRes(Arrays.asList(new BaseResponse()));
//
//        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), eq(request), eq(ProfileValidationsResp.class)))
//                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));
//
//        // Act
//        ProfileValidationsResp result = validateProfileService.validateProfile(requestDto);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(mockResponse, result);
//        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.POST), eq(request), eq(ProfileValidationsResp.class));
//    }
//
//    @Test
//    void testValidateProfile_BadRequestException() {
//        ProfileRequestDto requestDto = new ProfileRequestDto();
//        requestDto.setLegalName("BadRequestCompany");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<ProfileRequestDto> request = new HttpEntity<>(requestDto, headers);
//
//        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), eq(request), eq(ProfileValidationsResp.class)))
//                .thenThrow(new RestClientResponseException("Bad Request", 400, "Bad Request", null, null, null));
//        assertThrows(BadRequestException.class, () -> validateProfileService.validateProfile(requestDto));
//        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.POST), eq(request), eq(ProfileValidationsResp.class));
//    }
//
//    @Test
//    void testValidateProfile_ApplicationException() {
//        // Arrange
//        ProfileRequestDto requestDto = new ProfileRequestDto();
//        requestDto.setLegalName("ApplicationExceptionCompany");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<ProfileRequestDto> request = new HttpEntity<>(requestDto, headers);
//
//        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), eq(request), eq(ProfileValidationsResp.class)))
//                .thenThrow(new RestClientResponseException("Internal Server Error", 500, "Internal Server Error", null, null, null));
//
//        // Act & Assert
//        assertThrows(ApplicationException.class, () -> validateProfileService.validateProfile(requestDto));
//        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.POST), eq(request), eq(ProfileValidationsResp.class));
//    }
//
//    @Test
//    public void testValidateProfile_Exception() {
//        // Arrange
//        ProfileRequestDto requestDto = new ProfileRequestDto();
//        requestDto.setLegalName("ExceptionCompany");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<ProfileRequestDto> request = new HttpEntity<>(requestDto, headers);
//
//        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), eq(request), eq(ProfileValidationsResp.class)))
//                .thenThrow(new RuntimeException("Test Exception"));
//
//        // Act & Assert
//        assertThrows(RuntimeException.class, () -> validateProfileService.validateProfile(requestDto));
////        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.POST), eq(request), eq(ProfileValidationsResp.class));
//    }
}

