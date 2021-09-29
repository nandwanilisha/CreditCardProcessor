package com.creditcard.processor.rest;

import com.creditcard.processor.domain.CreateCardRequest;
import com.creditcard.processor.exception.InvalidRequestException;
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
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(DataProviderRunner.class)
public class CreditCardAccountControllerTest {

    @InjectMocks
    CreditCardAccountController controller;

    @Mock
    CreditCardOperations operations;

    static final CreateCardRequest validRequest = new CreateCardRequest();
    static final CreateCardRequest emptyRequest = new CreateCardRequest();
    static final CreateCardRequest noLimit = new CreateCardRequest();
    static final CreateCardRequest noName = new CreateCardRequest();
    static final CreateCardRequest noCard = new CreateCardRequest();

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @DataProvider
    public static Object[][] creditCardRequests(){
        validRequest.setCardNumber("4012888888881881");
        validRequest.setName("Lisha");
        validRequest.setLimit(10000L);
        noLimit.setCardNumber("4012888888881881");
        noLimit.setName("Lisha");
        noName.setCardNumber("1111233333333");
        noName.setLimit(1000L);
        noCard.setLimit(1000L);
        noCard.setName("lisha");

        return new Object[][]{
                {emptyRequest, InvalidRequestException.class},
                {noLimit, InvalidRequestException.class},
                {noName, InvalidRequestException.class},
                {noCard, InvalidRequestException.class}

        };
    }

    @Test
    @UseDataProvider("creditCardRequests")
    public void test_creditCard_CreateInvalid(CreateCardRequest request, Class classExpected){
        boolean check = false;
        try{
            ResponseEntity responseEntity = controller.createAccount(request);
        }catch(RuntimeException re){
            assertEquals(classExpected, re.getClass());
            check = true;
        }
        verifyNoInteractions(operations);
        assertTrue(check);
    }

    @Test
    public void test_creditCard_valid(){
        ResponseEntity responseEntity = controller.createAccount(validRequest);
        verify(operations).saveCreditCard(validRequest);
    }

    @Test
    public void test_getCreditCards(){
        ResponseEntity responseEntity = controller.getAllCreditCard();
        verify(operations).getCreditCard();
    }

}
