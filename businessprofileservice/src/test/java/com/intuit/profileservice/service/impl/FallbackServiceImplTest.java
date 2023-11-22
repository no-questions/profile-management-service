package com.intuit.profileservice.service.impl;

import com.intuit.profileservice.models.ErrorCodes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class FallbackServiceImplTest {

    @Mock
    private ErrorCodesServiceImpl errorCodesServiceImpl;

    @InjectMocks
    private FallbackServiceImpl fallbackService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFallbackForErrorMessage_ExistingErrorCode() {
        // Arrange
        String errorCode = "00";
        ErrorCodes expectedErrorCodes = new ErrorCodes(errorCode, "Description1", false, false);

        when(errorCodesServiceImpl.findByErrorCode(errorCode)).thenReturn(expectedErrorCodes);

        // Act
        String result = fallbackService.fallbackForErrorMessage(errorCode);

        // Assert
        assertEquals(expectedErrorCodes.getErrormessage(), result);
    }

    @Test
    void testFallbackForErrorMessage_NullErrorCode() {
        // Arrange
        String errorCode = null;

        // Act
        String result = fallbackService.fallbackForErrorMessage(errorCode);

        // Assert
        assertEquals("", result);
    }

    @Test()
    void testFallbackForErrorMessage_NonExistingErrorCode() {
        // Arrange
        String errorCode = "NonExistingCode";

        when(errorCodesServiceImpl.findByErrorCode(errorCode)).thenReturn(null);

        // Act
        String result = fallbackService.fallbackForErrorMessage(errorCode);

        // Assert
        assertEquals("", result);
    }
}
