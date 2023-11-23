package com.intuit.cacheservice.service.impl;

import com.intuit.cacheservice.dto.ErrorCodeDto;
import com.intuit.cacheservice.dto.FetchErrorCodeDBResp;
import com.intuit.cacheservice.models.ErrorCodesCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class DataLoadingServiceImplTest {

    @InjectMocks
    private DataLoadingServiceImpl dataLoadingService;

    @Mock
    private Logger logger;

    @Mock
    private RestTemplate restTemplate;

    @Value("${intuit.profileservice.getallerrorcodes}")
    private String getAllErrorCodesURI;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadErrorCodeData_Success() {
        // Arrange
        FetchErrorCodeDBResp fetchErrorCodeDBResp = new FetchErrorCodeDBResp();
        fetchErrorCodeDBResp.setErrorCodes(List.of(new ErrorCodeDto()));
        when(restTemplate.getForEntity(getAllErrorCodesURI, FetchErrorCodeDBResp.class)).thenReturn(new ResponseEntity<>(fetchErrorCodeDBResp, HttpStatus.OK));

        // Act
        List<ErrorCodesCache> result = dataLoadingService.loadErrorCodeData();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
//        verify(logger, times(1)).info("retrieving data using loadErrorCodeData");
    }

    @Test
    void testLoadErrorCodeData_RestClientResponseException() {
        // Arrange
        when(restTemplate.getForEntity(getAllErrorCodesURI, FetchErrorCodeDBResp.class)).thenThrow(new RestClientResponseException("Client error", HttpStatus.BAD_REQUEST.value(), "Bad Request", null, null, null));

        // Act and Assert
        assertThrows(RestClientResponseException.class, () -> dataLoadingService.loadErrorCodeData());
//        verify(logger, times(1)).info("retrieving data using loadErrorCodeData");
    }

    @Test
    void testLoadErrorCodeData_FallbackMethod() {
        // Arrange
        when(restTemplate.getForEntity(getAllErrorCodesURI, FetchErrorCodeDBResp.class)).thenThrow(new RestClientResponseException("Client error", HttpStatus.BAD_REQUEST.value(), "Bad Request", null, null, null));

        // Act
        List<ErrorCodesCache> result = dataLoadingService.loadErrorCodeData();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
//        verify(logger, times(1)).info("Entering fallback method: dataToBeLoadedFromInMemory");
//        verify(logger, times(1)).info("Exiting fallback method: dataToBeLoadedFromInMemory");
    }
}
