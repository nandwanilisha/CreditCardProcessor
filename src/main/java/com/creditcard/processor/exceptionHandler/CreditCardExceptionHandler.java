package com.creditcard.processor.exceptionHandler;

import com.creditcard.processor.domain.CreateCardResponse;
import com.creditcard.processor.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CreditCardExceptionHandler {

    @ExceptionHandler(value = {InvalidLengthException.class})
    public ResponseEntity<Object> handleInvalidLengthException(InvalidLengthException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CreateCardResponse("Length of credit card number is invalid."));
    }

    @ExceptionHandler(value = {InvalidCardNumberException.class})
    public ResponseEntity<Object> handleInvalidCardNumberException(InvalidCardNumberException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CreateCardResponse("Characters of credit card number is invalid."));
    }

    @ExceptionHandler(value = {LuhnValidationFailedException.class})
    public ResponseEntity<Object> handleLuhnException(LuhnValidationFailedException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CreateCardResponse("Credit card number is invalid."));
    }

    @ExceptionHandler(value = {InvalidRequestException.class})
    public ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CreateCardResponse("Invalid card request"));
    }

    @ExceptionHandler(value = {EncryptionFailedException.class})
    public ResponseEntity<Object> handleEncryptionException(EncryptionFailedException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CreateCardResponse("Encryption Failed."));
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityException(DataIntegrityViolationException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CreateCardResponse("Credit Card number is already in use."));
    }
}
