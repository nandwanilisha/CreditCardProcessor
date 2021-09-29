package com.creditcard.processor.exception;

public class LuhnValidationFailedException extends RuntimeException{
    public LuhnValidationFailedException(String message) {
        super(message);
    }
}
