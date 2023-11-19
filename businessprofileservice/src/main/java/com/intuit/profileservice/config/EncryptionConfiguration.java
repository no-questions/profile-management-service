package com.intuit.profileservice.config;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.intuit.profileservice.util.Constants;

@Configuration
public class EncryptionConfiguration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    byte[] salt = new byte[] { 0x5a, 0x3f, 0x19, 0x7e, 0x2d, 0x1a, 0x7c, 0x4b };

    @Bean
    SecretKey getSecretKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        try{
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(Constants.KEY.toCharArray(), salt, 65536, 256);
            return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), Constants.CIPHER);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("Error occured in EncryptionConfiguration.getSecretKey", e.getMessage());
            throw e;
        }
    }

    public SecretKey createSecretKey(byte[] keyBytes) {
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, Constants.CIPHER);
    }
}
