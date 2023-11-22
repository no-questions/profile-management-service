package com.intuit.profileservice.repository.listener;

import com.intuit.profileservice.models.TaxIdentifier;
import com.intuit.profileservice.util.EncryptDecryptUtil;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaxIdentifierListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EncryptDecryptUtil encryptDecryptUtil;


    @PrePersist
    @PreUpdate
    private void beforeAnyUpdate(TaxIdentifier tx) {
        tx.setIdentifier(encryptDecryptUtil.encrypt(tx.getIdentifier()));
    }

    @PostLoad
    @PostPersist
    private void afterAnyUpdate(TaxIdentifier tx) {
        tx.setIdentifier(encryptDecryptUtil.decrypt(tx.getIdentifier()));
    }
}
