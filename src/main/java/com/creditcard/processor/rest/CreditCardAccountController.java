package com.creditcard.processor.rest;

import com.creditcard.processor.domain.*;
import com.creditcard.processor.service.CreditCardOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class CreditCardAccountController {

    @Autowired
    CreditCardOperations creditCardOperations;

    @PostMapping(value = "accounts/credit-card", produces = "application/json", consumes = "application/json")
    public ResponseEntity<CreateCardResponse> createAccount(@RequestBody CreateCardRequest cardDetails){
        return creditCardOperations.saveCreditCard(cardDetails);
    }

    @GetMapping(value = "accounts/credit-cards", produces = "application/json")
    public ResponseEntity<List<CardResponse>> getAllCreditCard(){
        return creditCardOperations.getCreditCard();
    }

    @GetMapping(value = "accounts/credit-card/encrypt-helper", produces = "application/json", consumes = "application/json")
    public ResponseEntity<EncryptResponse> getEncryptedCreditCard(@RequestBody EncryptRequest request){
        return creditCardOperations.getEncryptedCardNumber(request);
    }

}
