package com.creditcard.processor.serviceimpl;

import com.creditcard.processor.domain.*;
import com.creditcard.processor.entity.CreditCardAccount;
import com.creditcard.processor.exception.EncryptionFailedException;
import com.creditcard.processor.exception.InvalidCardNumberException;
import com.creditcard.processor.exception.InvalidLengthException;
import com.creditcard.processor.exception.LuhnValidationFailedException;
import com.creditcard.processor.repo.CreditCardAccountRepo;
import com.creditcard.processor.service.CreditCardOperations;
import com.creditcard.processor.service.CreditCardValidations;
import com.creditcard.processor.service.EncryptionDecryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreditCardOperationsImpl implements CreditCardOperations {

    @Autowired
    CreditCardValidations validations;

    @Autowired
    CreditCardAccountRepo repo;

    @Autowired
    EncryptionDecryptionService encryptionDecryptionService;

    @Override
    public void saveCreditCard(CreateCardRequest cardDetails) {
        String decryptedCard = encryptionDecryptionService.decrypt(cardDetails.getCardNumber());
        if(!StringUtils.hasLength(decryptedCard) || !validations.isLengthValid(decryptedCard)){
            throw new InvalidLengthException("Length of the card number is not valid");
        }
        if(!validations.areCharactersVaild(decryptedCard)) {
            throw new InvalidCardNumberException("Card Number must contain numeric numbers only");
        }
        if(!validations.isLuhnVaild(decryptedCard)){
            throw new LuhnValidationFailedException("Card Number is not valid.");
        }

        CreditCardAccount account = new CreditCardAccount();
        account.setName(cardDetails.getName());
        account.setCardNumber(encryptionDecryptionService.encrypt(decryptedCard));
        account.setBalance(0L);
        account.setLimit(cardDetails.getLimit());

        if(!StringUtils.hasLength(account.getCardNumber())){
            throw new EncryptionFailedException("Encryption Failed.");
        }

        repo.save(account);
    }

    @Override
    public List<CardResponse> getCreditCard() {
        Iterable<CreditCardAccount> items = repo.findAll();

        List<CardResponse> cards = new ArrayList<>();
        items.forEach(item ->
        {
            String cardNumber = encryptionDecryptionService.decrypt(item.getCardNumber());
            if (StringUtils.hasLength(cardNumber)) {
                cards.add(new CardResponse(item.getName(), cardNumber, item.getBalance(), item.getLimit()));
            }
        });
        return cards;
    }

    @Override
    public String getEncryptedCardNumber(EncryptRequest request) {
        String encryptedCardNumber = encryptionDecryptionService.encrypt(request.getCardNumber());
        if(StringUtils.hasLength(encryptedCardNumber)){
            return encryptedCardNumber;
        }
        return null;
    }
}
