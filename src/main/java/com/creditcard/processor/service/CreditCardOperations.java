package com.creditcard.processor.service;

import com.creditcard.processor.domain.CreateCardRequest;
import com.creditcard.processor.domain.CreateCardResponse;
import org.springframework.http.ResponseEntity;

public interface CreditCardOperations {
    ResponseEntity<CreateCardResponse> saveCreditCard(CreateCardRequest cardNumber);
}
