package com.intuit.profileservice.config;

import com.intuit.profileservice.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

@Configuration
public class EncryptionConfiguration {

    // Logger for capturing and logging errors
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Bean definition for obtaining a secret key using PBKDF2 key derivation function.
     *
     * @return A secret key generated using PBKDF2 with HmacSHA256.
     * @throws NoSuchAlgorithmException if the specified algorithm is not available.
     * @throws InvalidKeySpecException  if the provided key specification is invalid.
     */
    @Bean
    SecretKey getSecretKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(Constants.KEY.toCharArray(), Constants.SALT, 65536, 256);
            return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), Constants.CIPHER);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // Log an error message if an exception occurs during secret key generation
            logger.error("Error occurred in EncryptionConfiguration.getSecretKey: {}", e.getStackTrace());
            throw e;
        }
    }

    /**
     * Creates a secret key from the provided key bytes.
     *
     * @param keyBytes The byte array representing the secret key.
     * @return A secret key created from the provided key bytes.
     */
    public SecretKey createSecretKey(byte[] keyBytes) {
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, Constants.CIPHER);
    }
}
