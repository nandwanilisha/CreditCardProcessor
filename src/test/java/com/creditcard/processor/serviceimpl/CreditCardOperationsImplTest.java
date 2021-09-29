package com.creditcard.processor.serviceimpl;

import com.creditcard.processor.domain.CreateCardRequest;
import com.creditcard.processor.entity.CreditCardAccount;
import com.creditcard.processor.exception.InvalidCardNumberException;
import com.creditcard.processor.exception.InvalidLengthException;
import com.creditcard.processor.exception.LuhnValidationFailedException;
import com.creditcard.processor.repo.CreditCardAccountRepo;
import com.creditcard.processor.service.CreditCardValidations;
import com.creditcard.processor.service.EncryptionDecryptionService;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

    @Mock
    EncryptionDecryptionService encryptionDecryptionService;

    static final CreateCardRequest validRequest = new CreateCardRequest();
    static final CreateCardRequest emptyRequest = new CreateCardRequest();
    static final CreateCardRequest outOfBoundRequest = new CreateCardRequest();
    static final CreateCardRequest invalidCardRequest = new CreateCardRequest();
    static final CreateCardRequest charactersRequest = new CreateCardRequest();

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
        validRequest.setCardNumber("4386280031035813");
        emptyRequest.setCardNumber("");
        outOfBoundRequest.setCardNumber("4012888888881881123456789109873");
        invalidCardRequest.setCardNumber("1111233333333");
        charactersRequest.setCardNumber("1973jdmsk097");
        when(validations.isLengthValid(validRequest.getCardNumber())).thenReturn(true);
        when(validations.areCharactersVaild(validRequest.getCardNumber())).thenReturn(true);
        when(validations.isLuhnVaild(validRequest.getCardNumber())).thenReturn(true);
        when(validations.isLengthValid(emptyRequest.getCardNumber())).thenReturn(false);
        when(validations.isLengthValid(outOfBoundRequest.getCardNumber())).thenReturn(false);
        when(validations.isLengthValid(invalidCardRequest.getCardNumber())).thenReturn(true);
        when(validations.areCharactersVaild(invalidCardRequest.getCardNumber())).thenReturn(true);
        when(validations.isLuhnVaild(invalidCardRequest.getCardNumber())).thenReturn(false);
        when(validations.isLengthValid(charactersRequest.getCardNumber())).thenReturn(true);
        when(validations.areCharactersVaild(charactersRequest.getCardNumber())).thenReturn(false);
        when(encryptionDecryptionService.decrypt("4386280031035813")).thenReturn("4386280031035813");
        when(encryptionDecryptionService.encrypt("4386280031035813")).thenReturn("4386280031035813");
        when(encryptionDecryptionService.decrypt("")).thenReturn("");
        when(encryptionDecryptionService.decrypt("4012888888881881123456789109873")).thenReturn("4012888888881881123456789109873");
        when(encryptionDecryptionService.decrypt("1973jdmsk097")).thenReturn("1973jdmsk097");
        when(encryptionDecryptionService.decrypt("1111233333333")).thenReturn("1111233333333");
    }

    @DataProvider
    public static Object[][] creditCardRequests(){
        return new Object[][]{
                {emptyRequest, InvalidLengthException.class},
                {outOfBoundRequest, InvalidLengthException.class},
                {invalidCardRequest, LuhnValidationFailedException.class},
                {charactersRequest, InvalidCardNumberException.class}
        };
    }

    @Test
    @UseDataProvider("creditCardRequests")
    public void test_saveCreditCard_invalidCases(CreateCardRequest request, Class expectedStatus){
        boolean check = false;
        try{
            operations.saveCreditCard(request);
        }catch(RuntimeException ex){
           assertEquals(expectedStatus, ex.getClass());
           check = true;
        }
        assertTrue(check);
    }

    @Test
    public void test_saveCreditCard_validCase(){
        operations.saveCreditCard(validRequest);
        verify(validations).isLengthValid(validRequest.getCardNumber());
        verify(validations).areCharactersVaild(validRequest.getCardNumber());
        verify(validations).isLuhnVaild(validRequest.getCardNumber());
        verify(repo).save(any());
    }

    @Test
    public void test_getCreditCard_whenNoData(){
        when(repo.findAll()).thenReturn(new ArrayList<>());
        operations.getCreditCard();
        verify(repo).findAll();
    }

    @Test
    public void test_getCreditCard_whenDataIsPresent(){
        CreditCardAccount account = new CreditCardAccount();
        account.setCardNumber("JnAMaEFVbFT+Cm1iALJ8OiXTiho6jIWNckM9Uh2yzVo=");
        account.setBalance(100L);
        when(repo.findAll()).thenReturn(new ArrayList<>());
        operations.getCreditCard();
        verify(repo).findAll();
    }

}
