package com.creditcard.processor.rest;

import com.creditcard.processor.domain.CreateCardRequest;
import com.creditcard.processor.domain.CreateCardResponse;
import com.creditcard.processor.service.CreditCardOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CreditCardAccountController {

    @Autowired
    CreditCardOperations creditCardOperations;

    @PostMapping(value = "accounts/credit-card", produces = "application/json", consumes = "application/json")
    public ResponseEntity<CreateCardResponse> createAccount(@RequestBody CreateCardRequest cardDetails){
        return creditCardOperations.saveCreditCard(cardDetails);
    }


}
