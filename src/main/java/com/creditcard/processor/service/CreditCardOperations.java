package com.creditcard.processor.service;

import com.creditcard.processor.domain.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CreditCardOperations {
    ResponseEntity<CreateCardResponse> saveCreditCard(CreateCardRequest cardNumber);
    ResponseEntity<List<CardResponse>> getCreditCard();
    ResponseEntity<EncryptResponse> getEncryptedCardNumber(EncryptRequest request);
}
