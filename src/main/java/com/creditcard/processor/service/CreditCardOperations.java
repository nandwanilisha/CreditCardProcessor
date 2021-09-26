package com.creditcard.processor.service;

import com.creditcard.processor.domain.CardResponse;
import com.creditcard.processor.domain.CreateCardRequest;
import com.creditcard.processor.domain.CreateCardResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CreditCardOperations {
    ResponseEntity<CreateCardResponse> saveCreditCard(CreateCardRequest cardNumber);
    ResponseEntity<List<CardResponse>> getCreditCard();
}
