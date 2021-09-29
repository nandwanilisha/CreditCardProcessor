package com.creditcard.processor.service;

import com.creditcard.processor.domain.*;

import java.util.List;

public interface CreditCardOperations {
    void saveCreditCard(CreateCardRequest cardNumber);
    List<CardResponse> getCreditCard();
    String getEncryptedCardNumber(EncryptRequest request);
}
