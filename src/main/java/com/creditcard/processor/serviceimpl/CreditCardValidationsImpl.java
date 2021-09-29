package com.creditcard.processor.serviceimpl;

import com.creditcard.processor.service.CreditCardValidations;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CreditCardValidationsImpl implements CreditCardValidations {
    @Override
    public boolean isLengthValid(String cardNumber) {
        return cardNumber.length()>0 && cardNumber.length() <= 19;
    }

    @Override
    public boolean areCharactersVaild(String cardNumber) {
        Pattern numerics = Pattern.compile("\\d*");
        Matcher source = numerics.matcher(cardNumber);
        return source.matches();
    }

    @Override
    public boolean isLuhnVaild(String cardNumber) {
        int sum = 0;
        boolean secondDigit = false;
        for (int i = cardNumber.length()-1 ; i >= 0; i--) {
            int digit = (int) cardNumber.charAt(i) -48;
            if(secondDigit){
                digit *= 2;
                sum += digit % 10 + digit / 10;
                secondDigit = false;
            }
            else {
                sum += digit;
                secondDigit = true;
            }
        }
        return sum % 10 == 0;
    }
}
