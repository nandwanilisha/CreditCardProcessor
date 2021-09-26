package com.creditcard.processor.rest;

import com.creditcard.processor.domain.CreateCardRequest;
import com.creditcard.processor.domain.CreateCardResponse;
import com.creditcard.processor.service.CreditCardOperations;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class CreditCardAccountControllerTest {

    @InjectMocks
    CreditCardAccountController controller;

    @Mock
    CreditCardOperations operations;

    static CreateCardRequest validRequest = new CreateCardRequest();
    static CreateCardRequest emptyRequest = new CreateCardRequest();
    static CreateCardRequest outOfBoundRequest = new CreateCardRequest();
    static CreateCardRequest invalidCardRequest = new CreateCardRequest();

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
        when(operations.saveCreditCard(validRequest)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(new CreateCardResponse()));
        when(operations.saveCreditCard(emptyRequest)).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CreateCardResponse()));
        when(operations.saveCreditCard(outOfBoundRequest)).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CreateCardResponse()));
        when(operations.saveCreditCard(invalidCardRequest)).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CreateCardResponse()));
    }

    @DataProvider
    public static Object[][] creditCardRequests(){
        validRequest.setCardNumber("4012888888881881");
        emptyRequest.setCardNumber("");
        outOfBoundRequest.setCardNumber("4012888888881881123456789109873");
        invalidCardRequest.setCardNumber("1111233333333");

        return new Object[][]{
                {validRequest, HttpStatus.CREATED},
                {emptyRequest, HttpStatus.BAD_REQUEST},
                {outOfBoundRequest, HttpStatus.BAD_REQUEST},
                {invalidCardRequest, HttpStatus.BAD_REQUEST}
        };
    }

    @Test
    @UseDataProvider("creditCardRequests")
    public void test_creditCard_Create(CreateCardRequest request, HttpStatus expectedStatus){
        ResponseEntity responseEntity = controller.createAccount(request);
        assertEquals(expectedStatus, responseEntity.getStatusCode());
        verify(operations).saveCreditCard(request);
    }

    @Test
    public void test_getCreditCards(){
        ResponseEntity responseEntity = controller.getAllCreditCard();
        verify(operations).getCreditCard();
    }

}
