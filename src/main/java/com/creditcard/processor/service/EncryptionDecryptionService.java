package com.creditcard.processor.service;

public interface EncryptionDecryptionService {
    String encrypt(String strToEncrypt);
    String decrypt(String strToDecrypt);
}
