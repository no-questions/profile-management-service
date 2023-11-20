package com.intuit.businessprofileservice.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import com.intuit.profileservice.service.impl.RateCheckerImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

class RateCheckerImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RateCheckerImpl rateChecker;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUpdateRate_Success() {
        String customerId = "123";
        String action = "increment";
        boolean increment = true;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(Boolean.class)))
                .thenReturn(new ResponseEntity<>(true, HttpStatus.OK));
        Boolean result = rateChecker.getUpdateRate(customerId, action, increment);

        assertTrue(result);
        verify(restTemplate, times(1)).exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(Boolean.class));
    }

    @Test
    void testGetUpdateRate_Failure() {
        String customerId = "456";
        String action = "decrement";
        boolean increment = false;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(Boolean.class)))
                .thenReturn(new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR));

        Boolean result = rateChecker.getUpdateRate(customerId, action, increment);

        assertFalse(result);
        verify(restTemplate, times(1)).exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(Boolean.class));
    }

    @Test
    void testGetUpdateRate_Exception() {
        String customerId = "789";
        String action = "reset";
        boolean increment = true;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(Boolean.class)))
                .thenThrow(new RuntimeException("Test Exception"));

        assertThrows(RuntimeException.class, () -> rateChecker.getUpdateRate(customerId, action, increment));
        verify(restTemplate, times(1)).exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(Boolean.class));
    }
}

