package com.intuit.cacheservice.service;

import com.intuit.cacheservice.exceptions.ApplicationException;
import com.intuit.cacheservice.exceptions.BadRequestException;
import com.intuit.cacheservice.models.ErrorCodesCache;
import com.intuit.cacheservice.repository.ErrorCodesCacheRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientResponseException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ErrorCodesCacheServiceTest {

    @InjectMocks
    private ErrorCodesCacheService errorCodesCacheService;

    @Mock
    private ErrorCodesCacheRepository errorCodesCacheRepository;

    @Mock
    private DataLoadingService dataLoadingService;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetByErrorCode() {
        // Arrange
        String errorCode = "404";
        ErrorCodesCache expectedError = ErrorCodesCache.builder().errorcode("404").errormessage("Success").isfailure(false).isretryeligible(false).build();
        when(errorCodesCacheRepository.findByErrorcode(errorCode)).thenReturn(expectedError);

        // Act
        ErrorCodesCache result = errorCodesCacheService.getByErrorCode(errorCode);

        // Assert
        assertEquals(expectedError, result);
        verify(errorCodesCacheRepository, times(1)).findByErrorcode(errorCode);
    }

    @Test
    void testGetAllErrorCodes() {
        // Arrange
        List<ErrorCodesCache> expectedErrorCodes = Arrays.asList(ErrorCodesCache.builder().errorcode("00").errormessage("Success").isfailure(false).isretryeligible(false).build(), ErrorCodesCache.builder().errorcode("001").errormessage("Success").isfailure(false).isretryeligible(false).build());
        when(errorCodesCacheRepository.findAll()).thenReturn(expectedErrorCodes);

        // Act
        List<ErrorCodesCache> result = errorCodesCacheService.getAllErrorCodes();

        // Assert
        assertEquals(expectedErrorCodes, result);
        verify(errorCodesCacheRepository, times(1)).findAll();
    }

    @Test
    void testRefreshCache_Success() {
        // Arrange
        List<ErrorCodesCache> errorCodes = Arrays.asList(ErrorCodesCache.builder().errorcode("00").errormessage("Success").isfailure(false).isretryeligible(false).build(), ErrorCodesCache.builder().errorcode("001").errormessage("Success").isfailure(false).isretryeligible(false).build());
        when(dataLoadingService.loadErrorCodeData()).thenReturn(errorCodes);

        // Act
        errorCodesCacheService.refreshCache();

        // Assert
        verify(errorCodesCacheRepository, times(1)).saveAll(errorCodes);
    }

    @Test
    void testRefreshCache_RestClientResponseException_4xxClientError() {
        // Arrange
        when(dataLoadingService.loadErrorCodeData()).thenThrow(new RestClientResponseException("Client error", HttpStatus.BAD_REQUEST.value(), "Bad Request", null, null, null));

        // Act and Assert
        BadRequestException exception = assertThrows(BadRequestException.class, () -> errorCodesCacheService.refreshCache());
        assertEquals("BR", exception.getErrorCode());
    }

    @Test
    void testRefreshCache_RestClientResponseException_OtherError() {
        // Arrange
        when(dataLoadingService.loadErrorCodeData()).thenThrow(new RestClientResponseException("Server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", null, null, null));

        // Act and Assert
        ApplicationException exception = assertThrows(ApplicationException.class, () -> errorCodesCacheService.refreshCache());
        assertEquals("FE", exception.getErrorCode());

    }
}