package com.intuit.businessprofileservice.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.intuit.profileservice.dto.ErrorCodeDto;
import com.intuit.profileservice.exceptions.ApplicationException;
import com.intuit.profileservice.service.FallbackService;
import com.intuit.profileservice.service.impl.GetErrorMessagesCache;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetErrorMessagesCacheTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private FallbackService fallbackService;

    @InjectMocks
    private GetErrorMessagesCache getErrorMessagesCache;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchErrorMessage_Success() {
        // Arrange
        String errorCode = "E001";
        ErrorCodeDto expectedResult = new ErrorCodeDto(errorCode, "Description1", false, true);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        when(restTemplate.getForEntity(anyString(), eq(ErrorCodeDto.class), any(HttpEntity.class)))
                .thenReturn(new ResponseEntity<>(expectedResult, HttpStatus.OK));

        // Act
        String result = getErrorMessagesCache.fetchErrorMessage(errorCode);

        // Assert
        assertEquals(expectedResult.getErrormessage(), result);
    }

    @Test
    void testFetchErrorMessage_Fallback_Success() {
        // Arrange
        String errorCode = "E002";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        when(restTemplate.getForEntity(anyString(), eq(ErrorCodeDto.class), any(HttpEntity.class)))
                .thenThrow(new RestClientResponseException("Client Error", HttpStatus.BAD_REQUEST.value(), "Client Error", null, null, null));

        when(fallbackService.fallbackForErrorMessage(errorCode)).thenReturn("Fallback Error Message");

        // Act
        String result = getErrorMessagesCache.fetchErrorMessage(errorCode);

        // Assert
        assertEquals("Fallback Error Message", result);
    }

    @Test
    void testFetchErrorMessage_Fallback_Exception() {
        // Arrange
        String errorCode = "E003";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        when(restTemplate.getForEntity(anyString(), eq(ErrorCodeDto.class), any(HttpEntity.class)))
                .thenThrow(new RestClientResponseException("Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error", null, null, null));

        when(fallbackService.fallbackForErrorMessage(errorCode)).thenThrow(new ApplicationException("Fallback Exception"));

        // Act & Assert
        assertThrows(ApplicationException.class, () -> getErrorMessagesCache.fetchErrorMessage(errorCode));
    }

    @Test
    void testFallbackForErrorMessage_Success() {
        // Arrange
        String errorCode = "E004";

        when(fallbackService.fallbackForErrorMessage(errorCode)).thenReturn("Fallback Error Message");

        // Act
//        String result = getErrorMessagesCache.fallbackForErrorMessage(errorCode);

        // Assert
//        assertEquals("Fallback Error Message", result);
    }
}
