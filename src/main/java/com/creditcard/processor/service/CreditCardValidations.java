package com.creditcard.processor.service;

public interface CreditCardValidations {
    boolean isLengthValid(String cardNumber);
    boolean areCharactersVaild(String cardNumber);
    boolean isLuhnVaild(String cardNumber);
}
