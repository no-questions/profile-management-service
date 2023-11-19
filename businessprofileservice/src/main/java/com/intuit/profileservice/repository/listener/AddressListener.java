package com.intuit.profileservice.repository.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.intuit.profileservice.models.Address;
import com.intuit.profileservice.service.AuditLogService;
import com.intuit.profileservice.util.EncryptDecryptUtil;

import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddressListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EncryptDecryptUtil encryptDecryptUtil;

    @PrePersist
    @PreUpdate
    private void beforeAnyUpdate (Address as) {
        as.setLine1(encryptDecryptUtil.encrypt(as.getLine1()));
        as.setLine2(encryptDecryptUtil.encrypt(as.getLine2()));
        as.setZip(encryptDecryptUtil.encrypt(as.getZip()));
        as.setCity(encryptDecryptUtil.encrypt(as.getCity()));
    }
    @PostLoad
    @PostPersist
    private void afterAnyUpdate (Address as) {
        as.setLine1(encryptDecryptUtil.decrypt(as.getLine1()));
        as.setLine2(encryptDecryptUtil.decrypt(as.getLine2()));
        as.setZip(encryptDecryptUtil.decrypt(as.getZip()));
        as.setCity(encryptDecryptUtil.decrypt(as.getCity()));
    }
}
