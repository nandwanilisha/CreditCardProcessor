package com.creditcard.processor.serviceimpl;

import com.creditcard.processor.domain.*;
import com.creditcard.processor.entity.CreditCardAccount;
import com.creditcard.processor.repo.CreditCardAccountRepo;
import com.creditcard.processor.service.CreditCardOperations;
import com.creditcard.processor.service.CreditCardValidations;
import com.creditcard.processor.service.EncryptionDecryptionService;
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

    @Autowired
    EncryptionDecryptionService encryptionDecryptionService;

    @Override
    public ResponseEntity<CreateCardResponse> saveCreditCard(CreateCardRequest cardDetails) {
        String decryptedCard = encryptionDecryptionService.decrypt(cardDetails.getEncryptedCardNumber());
        if(!StringUtils.hasLength(decryptedCard) && !validations.isLengthValid(decryptedCard)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CreateCardResponse("Length of the card number is not valid"));
        }
        if(!validations.areCharactersVaild(decryptedCard)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CreateCardResponse("Card Number must contain numeric numbers only"));
        }
        if(!validations.isLuhnVaild(decryptedCard)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CreateCardResponse("Card Number is not valid."));
        }

        CreditCardAccount account = new CreditCardAccount();
        account.setCardNumber(encryptionDecryptionService.encrypt(decryptedCard));
        account.setBalance(cardDetails.getBalance() != null && cardDetails.getBalance() >= 0
                ? cardDetails.getBalance() : 0);

        if(!StringUtils.hasLength(account.getCardNumber())){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CreateCardResponse("Encryption Failed."));
        }

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
            {
                String cardNumber = encryptionDecryptionService.decrypt(item.getCardNumber());
                if (StringUtils.hasLength(cardNumber)) {
                    cards.add(new CardResponse(cardNumber, item.getBalance()));
                }
            });
        }
        return ResponseEntity.status(HttpStatus.OK).body(cards);
    }

    @Override
    public ResponseEntity<EncryptResponse> getEncryptedCardNumber(EncryptRequest request) {
        String encryptedCardNumber = encryptionDecryptionService.encrypt(request.getCardNumber());
        if(StringUtils.hasLength(encryptedCardNumber)){
            return ResponseEntity.status(HttpStatus.OK).body(new EncryptResponse(encryptedCardNumber));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EncryptResponse());
    }
}
