package com.intuit.profileservice.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EncryptDecryptUtilTest {
    @Autowired
    EncryptDecryptUtil encryptDecryptUtil;

    @Test
    void testWithEmptyString() {
        assertEquals("", encryptDecryptUtil.encrypt(""));
    }

    @Test
    void equalEncryptionDecryption() {
        String toBeTested = "plaintext";
        assertEquals(toBeTested,encryptDecryptUtil.decrypt(encryptDecryptUtil.encrypt(toBeTested)));
    }

    @Test
    void testWithEmptyStringDecryption() {
        assertEquals("", encryptDecryptUtil.decrypt(""));
    }
}
