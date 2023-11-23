package com.intuit.validationservice.service;

import com.intuit.validationservice.Service.InvokeProductCalls;
import com.intuit.validationservice.dto.BaseResponse;
import com.intuit.validationservice.dto.CreateValidateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class InvokeProductCallsTest {

    @Mock
    private Logger logger;

    @Mock
    private Map<String, String> mapResCodes;
    @InjectMocks
    private InvokeProductCalls invokeProductCalls;


    @Test
    void invokeProductA_Success() {
        // Arrange
        CreateValidateDto request = new CreateValidateDto();
        when(mapResCodes.get("A")).thenReturn("01");

        // Act
        BaseResponse response = invokeProductCalls.invokeProductA(request);

        // Assert
        assertNotNull(response);
        assertEquals("01", response.getResCode());
//        verify(logger, times(1)).info("invokeProductA - Success: {}", response.getResMessage());
//        verifyNoInteractions(logger, response);
    }

    @Test
    void invokeProductB_Success() {
        // Arrange
        CreateValidateDto request = new CreateValidateDto();
        when(mapResCodes.get("B")).thenReturn("02");

        // Act
        BaseResponse response = invokeProductCalls.invokeProductB(request);

        // Assert
        assertNotNull(response);
        assertEquals("02", response.getResCode());
//        verify(logger, times(1)).info("invokeProductB - Success: {}", response.getResMessage());
//        verifyNoInteractions(logger, response);
    }

    @Test
    void invokeProductC_Success() {
        // Arrange
        CreateValidateDto request = new CreateValidateDto();
        when(mapResCodes.get("C")).thenReturn("03");


        // Act
        BaseResponse response = invokeProductCalls.invokeProductC(request);

        // Assert
        assertNotNull(response);
        assertEquals("03", response.getResCode());
//        verify(logger, times(1)).info("invokeProductC - Success: {}", response.getResMessage());
//        verifyNoInteractions(logger, response);
    }

    @Test
    void invokeProductD_Success() {
        // Arrange
        CreateValidateDto request = new CreateValidateDto();
        when(mapResCodes.get("D")).thenReturn("01");


        // Act
        BaseResponse response = invokeProductCalls.invokeProductD(request);

        // Assert
        assertNotNull(response);
        assertEquals("01", response.getResCode());
//        verify(logger, times(1)).info("invokeProductD - Success: {}", response.getResMessage());
//        verifyNoInteractions(logger, response);
    }

//    @Test
//    void fallBackForFailure_FallbackMethod() {
//        // Arrange
//        CreateValidateDto request = new CreateValidateDto();
//
//        // Act
//        BaseResponse response = invokeProductCalls.fallBackForFailure(request);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals("FE", response.getResCode());
//        assertEquals("Failure", response.getResMessage());
//        verifyNoInteractions(logger);
//    }
}