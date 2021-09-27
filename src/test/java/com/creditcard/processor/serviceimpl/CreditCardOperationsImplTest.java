package com.creditcard.processor.serviceimpl;

import com.creditcard.processor.domain.CreateCardRequest;
import com.creditcard.processor.entity.CreditCardAccount;
import com.creditcard.processor.repo.CreditCardAccountRepo;
import com.creditcard.processor.service.CreditCardValidations;
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

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class CreditCardOperationsImplTest {

    @InjectMocks
    CreditCardOperationsImpl operations;

    @Mock
    CreditCardValidations validations;

    @Mock
    CreditCardAccountRepo repo;

    static CreateCardRequest validRequest = new CreateCardRequest();
    static CreateCardRequest emptyRequest = new CreateCardRequest();
    static CreateCardRequest outOfBoundRequest = new CreateCardRequest();
    static CreateCardRequest invalidCardRequest = new CreateCardRequest();
    static CreateCardRequest charactersRequest = new CreateCardRequest();

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
        validRequest.setEncryptedCardNumber("JnAMaEFVbFT+Cm1iALJ8OiXTiho6jIWNckM9Uh2yzVo=");
        emptyRequest.setEncryptedCardNumber("");
        outOfBoundRequest.setEncryptedCardNumber("4012888888881881123456789109873");
        invalidCardRequest.setEncryptedCardNumber("1111233333333");
        charactersRequest.setEncryptedCardNumber("1973jdmsk097");
        when(validations.isLengthValid(validRequest.getEncryptedCardNumber())).thenReturn(true);
        when(validations.areCharactersVaild(validRequest.getEncryptedCardNumber())).thenReturn(true);
        when(validations.isLuhnVaild(validRequest.getEncryptedCardNumber())).thenReturn(true);
        when(validations.isLengthValid(emptyRequest.getEncryptedCardNumber())).thenReturn(false);
        when(validations.isLengthValid(outOfBoundRequest.getEncryptedCardNumber())).thenReturn(false);
        when(validations.isLengthValid(invalidCardRequest.getEncryptedCardNumber())).thenReturn(true);
        when(validations.areCharactersVaild(invalidCardRequest.getEncryptedCardNumber())).thenReturn(true);
        when(validations.isLuhnVaild(invalidCardRequest.getEncryptedCardNumber())).thenReturn(false);
        when(validations.isLengthValid(charactersRequest.getEncryptedCardNumber())).thenReturn(true);
        when(validations.areCharactersVaild(charactersRequest.getEncryptedCardNumber())).thenReturn(false);
    }

    @DataProvider
    public static Object[][] creditCardRequests(){
        return new Object[][]{
                {validRequest, HttpStatus.CREATED},
                {emptyRequest, HttpStatus.BAD_REQUEST},
                {outOfBoundRequest, HttpStatus.BAD_REQUEST},
                {invalidCardRequest, HttpStatus.BAD_REQUEST},
                {charactersRequest, HttpStatus.BAD_REQUEST}
        };
    }

    @Test
    @UseDataProvider("creditCardRequests")
    public void test_saveCreditCard(CreateCardRequest request, HttpStatus expectedStatus){
        ResponseEntity responseEntity = operations.saveCreditCard(request);
        assertEquals(expectedStatus, responseEntity.getStatusCode());
    }

    @Test
    public void test_getCreditCard_whenNoData(){
        when(repo.findAll()).thenReturn(null);
        ResponseEntity responseEntity = operations.getCreditCard();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void test_getCreditCard_whenDataIsPresent(){
        CreditCardAccount account = new CreditCardAccount();
        account.setCardNumber("JnAMaEFVbFT+Cm1iALJ8OiXTiho6jIWNckM9Uh2yzVo=");
        account.setBalance(100L);
        when(repo.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity responseEntity = operations.getCreditCard();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(repo).findAll();
    }

}
