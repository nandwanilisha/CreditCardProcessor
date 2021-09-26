package com.creditcard.processor.rest;

import com.creditcard.processor.domain.CreateCardRequest;
import com.creditcard.processor.domain.CreateCardResponse;
import com.creditcard.processor.domain.CardResponse;
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


}
