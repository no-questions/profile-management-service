package com.intuit.profileservice.repository.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.util.EncryptDecryptUtil;

import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProfileListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EncryptDecryptUtil encryptDecryptUtil;

    @PrePersist
    @PreUpdate
    private void beforeAnyUpdate (Profile ps) {
        ps.setEmail(encryptDecryptUtil.encrypt(ps.getEmail()));
    }
    @PostLoad
    @PostPersist
    private void afterAnyUpdate (Profile ps) {
        ps.setEmail(encryptDecryptUtil.decrypt(ps.getEmail()));
    }

}
