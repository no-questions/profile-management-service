package com.intuit.businessprofileservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.profileservice.models.AuditLog;
import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.repository.AuditLogRepository;
import com.intuit.profileservice.service.impl.AuditLogServiceImpl;
import com.intuit.profileservice.util.EncryptDecryptUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Async;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.UUID;

class AuditLogServiceImplTest {

    @Mock
    private AuditLogRepository auditLogRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private EncryptDecryptUtil encryptDecryptUtil;

    @Mock
    private Logger logger;

    @InjectMocks
    private AuditLogServiceImpl auditLogService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveToAuditLog_CreateAction() throws JsonProcessingException {
        Profile profile = new Profile();
        profile.setId(UUID.randomUUID());
        profile.setIsmodified(false);

        when(objectMapper.writeValueAsString(profile)).thenReturn("{}");
        when(encryptDecryptUtil.encrypt(anyString())).thenReturn("encryptedString");
        auditLogService.saveToAuditLog(profile);
        verify(auditLogRepository, times(1)).save(any(AuditLog.class));
    }

    @Test
    void testSaveToAuditLog_UpdateAction() throws JsonProcessingException {
        Profile profile = new Profile();
        profile.setId(UUID.randomUUID());
        profile.setIsmodified(true);
        when(objectMapper.writeValueAsString(profile)).thenReturn("{}");
        when(encryptDecryptUtil.encrypt(anyString())).thenReturn("encryptedString");
        auditLogService.saveToAuditLog(profile);
        verify(auditLogRepository, times(1)).save(any(AuditLog.class));
    }

    @Test
    void testConvertProfileToJson_Success() throws JsonProcessingException {
        Profile profile = new Profile();
        profile.setId(UUID.randomUUID());
        when(objectMapper.writeValueAsString(profile)).thenReturn("{}");
        String result = auditLogService.convertProfileToJson(profile);
        verify(logger, never()).error(anyString(), anyString(), anyString());
    }

    @Test
    void testConvertProfileToJson_Exception() throws JsonProcessingException {
        Profile profile = new Profile();
        profile.setId(UUID.randomUUID());
        when(objectMapper.writeValueAsString(profile)).thenThrow(JsonProcessingException.class);
        String result = auditLogService.convertProfileToJson(profile);
    }
}

