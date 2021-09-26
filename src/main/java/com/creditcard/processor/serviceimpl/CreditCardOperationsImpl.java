package com.creditcard.processor.serviceimpl;

import com.creditcard.processor.domain.CardResponse;
import com.creditcard.processor.domain.CreateCardRequest;
import com.creditcard.processor.domain.CreateCardResponse;
import com.creditcard.processor.entity.CreditCardAccount;
import com.creditcard.processor.repo.CreditCardAccountRepo;
import com.creditcard.processor.service.CreditCardOperations;
import com.creditcard.processor.service.CreditCardValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CreditCardOperationsImpl implements CreditCardOperations {

    @Autowired
    CreditCardValidations validations;

    @Autowired
    CreditCardAccountRepo repo;

    @Override
    public ResponseEntity<CreateCardResponse> saveCreditCard(CreateCardRequest cardDetails) {
        if(!StringUtils.hasLength(cardDetails.getCardNumber()) && !validations.isLengthValid(cardDetails.getCardNumber())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CreateCardResponse("Length of the card number is not valid"));
        }
        if(!validations.areCharactersVaild(cardDetails.getCardNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CreateCardResponse("Card Number must contain numeric numbers only"));
        }
        if(!validations.isLuhnVaild(cardDetails.getCardNumber())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CreateCardResponse("Card Number is not valid."));
        }

        CreditCardAccount account = new CreditCardAccount();
        account.setCardNumber(cardDetails.getCardNumber());
        account.setBalance(cardDetails.getBalance() != null && cardDetails.getBalance() >= 0
                ? cardDetails.getBalance() : 0);
        try{
            repo.save(account);
        }catch (DataIntegrityViolationException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CreateCardResponse("Card Number is already in use."));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateCardResponse("Successfully created."));
    }

    @Override
    public ResponseEntity<List<CardResponse>> getCreditCard() {
        Iterable<CreditCardAccount> items = repo.findAll();

        List<CardResponse> cards = new ArrayList<>();
        if(Optional.ofNullable(items).isPresent()){
            items.forEach(item ->
                   cards.add(new CardResponse(item.getCardNumber(), item.getBalance())));
        }
        return ResponseEntity.status(HttpStatus.OK).body(cards);
    }
}
