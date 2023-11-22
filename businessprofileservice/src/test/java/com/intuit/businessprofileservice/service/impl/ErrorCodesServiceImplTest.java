package com.intuit.businessprofileservice.service.impl;

import com.intuit.profileservice.models.ErrorCodes;
import com.intuit.profileservice.repository.ErrorCodesRepository;
import com.intuit.profileservice.service.impl.ErrorCodesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ErrorCodesServiceImplTest {

    @Mock
    private ErrorCodesRepository errorCodesRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ErrorCodesServiceImpl errorCodesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        List<ErrorCodes> expectedErrorCodes = List.of(
                new ErrorCodes("E001", "Description1", false, false),
                new ErrorCodes("E002", "Description2", true, true)
        );

        when(errorCodesRepository.findAll()).thenReturn(expectedErrorCodes);
        List<ErrorCodes> actualErrorCodes = errorCodesService.getAll();
        assertEquals(expectedErrorCodes, actualErrorCodes);
    }

    @Test
    void testFindByErrorCode() {
        String errorCode = "E001";
        ErrorCodes expectedErrorCodes = new ErrorCodes(errorCode, "Description1", false, true);

        when(errorCodesRepository.findByErrorcode(errorCode)).thenReturn(expectedErrorCodes);
        ErrorCodes actualErrorCodes = errorCodesService.findByErrorCode(errorCode);
        assertEquals(expectedErrorCodes, actualErrorCodes);
    }

    // @Test
    // public void testCheckForFailure_Success() {
    //     // Arrange
    //     ProfileValidationsResp resp = new ProfileValidationsResp();
    //     resp.setRes(List.of(new ValidationResult("00", "Success")));

    //     when(restTemplate.exchange(
    //             anyString(),
    //             eq(HttpMethod.POST),
    //             any(HttpEntity.class),
    //             eq(Boolean.class)
    //     )).thenReturn(new ResponseEntity<>(true, HttpStatus.OK));

    //     // Act
    //     boolean result = errorCodesService.checkForFailure(resp);

    //     // Assert
    //     assertTrue(result);
    // }

    // @Test
    // public void testCheckForFailure_ClientError() {
    //     // Arrange
    //     ProfileValidationsResp resp = new ProfileValidationsResp();
    //     resp.setRes(List.of(new ValidationResult("400", "Client Error")));

    //     when(restTemplate.exchange(
    //             anyString(),
    //             eq(HttpMethod.POST),
    //             any(HttpEntity.class),
    //             eq(Boolean.class)
    //     )).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Client Error"));

    //     // Act & Assert
    //     assertThrows(BadRequestException.class, () -> errorCodesService.checkForFailure(resp));
    // }

    // @Test
    // public void testCheckForFailure_ServerError() {
    //     // Arrange
    //     ProfileValidationsResp resp = new ProfileValidationsResp();
    //     resp.setRes(List.of(new ValidationResult("500", "Server Error")));

    //     when(restTemplate.exchange(
    //             anyString(),
    //             eq(HttpMethod.POST),
    //             any(HttpEntity.class),
    //             eq(Boolean.class)
    //     )).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error"));

    //     // Act & Assert
    //     assertThrows(ApplicationException.class, () -> errorCodesService.checkForFailure(resp));
    // }

    // @Test
    // public void testFallbackForFailureIdentification() {
    //     // Arrange
    //     ProfileValidationsResp resp = new ProfileValidationsResp();
    //     resp.setRes(List.of(new ValidationResult("DNE", "Description Not Exists")));

    //     // Act
    //     boolean result = errorCodesService.fallbackForFailureIdentification(resp);

    //     // Assert
    //     assertTrue(result);
    // }
}

