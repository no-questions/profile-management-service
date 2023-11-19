package com.intuit.profileservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.profileservice.util.EncryptDecryptUtil;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class EncryptDecryptController {
    private final EncryptDecryptUtil encryptDecryptUtil;
    @GetMapping("decrypt/string")
    public ResponseEntity<String> decryptString(String encryptedString) {
        return ResponseEntity.ok(encryptDecryptUtil.decrypt(encryptedString));
    }
    @GetMapping("encrypt/string")
    public ResponseEntity<String> encryptString(String decryptedString) {
        return ResponseEntity.ok(encryptDecryptUtil.encrypt(decryptedString));
    }
}
