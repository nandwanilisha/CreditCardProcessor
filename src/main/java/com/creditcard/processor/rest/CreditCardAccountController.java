package com.creditcard.processor.rest;

import com.creditcard.processor.domain.*;
import com.creditcard.processor.exception.InvalidRequestException;
import com.creditcard.processor.service.CreditCardOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class CreditCardAccountController {

    @Autowired
    CreditCardOperations creditCardOperations;

    @PostMapping(value = "accounts/credit-card", produces = "application/json", consumes = "application/json")
    public ResponseEntity<CreateCardResponse> createAccount(@RequestBody CreateCardRequest cardDetails){
        if(!StringUtils.hasLength(cardDetails.getName()) || !StringUtils.hasLength(cardDetails.getCardNumber())
            || cardDetails.getLimit() == null){
            throw new InvalidRequestException("Request must consist Name, Card Number, Limit.");
        }
        creditCardOperations.saveCreditCard(cardDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new CreateCardResponse("Successfully Added."));
    }

    @GetMapping(value = "accounts/credit-cards", produces = "application/json")
    public ResponseEntity<List<CardResponse>> getAllCreditCard(){
        List<CardResponse> cards = creditCardOperations.getCreditCard();
        return ResponseEntity.status(HttpStatus.OK).body(cards);
    }

    @GetMapping(value = "accounts/credit-card/encrypt-helper", produces = "application/json", consumes = "application/json")
    public ResponseEntity<EncryptResponse> getEncryptedCreditCard(@RequestBody EncryptRequest request){
        String encryptedCard = creditCardOperations.getEncryptedCardNumber(request);
        return ResponseEntity.status(HttpStatus.OK).body(new EncryptResponse(encryptedCard));
    }

}
