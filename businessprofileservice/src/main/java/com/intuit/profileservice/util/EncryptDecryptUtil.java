package com.intuit.profileservice.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EncryptDecryptUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    SecretKey secretKey;

    EncryptDecryptUtil(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public String encrypt(String data) {
        if(StringUtils.isBlank(data))
            return "";
        try {
            Cipher cipher = Cipher.getInstance(Constants.CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            logger.error(e.getMessage());
            return "";
        }
    }

    public String decrypt(String encryptedData) {
        if(StringUtils.isBlank(encryptedData))
            return "";
        try{
            Cipher cipher = Cipher.getInstance(Constants.CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
            logger.error(e.getMessage());
            return "";
        }
        
    }

    public boolean isEncrypted(String input) {
        try {
            Base64.getDecoder().decode(input);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
