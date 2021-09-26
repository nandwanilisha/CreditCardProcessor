package com.creditcard.processor.serviceimpl;

import com.creditcard.processor.domain.CreateCardRequest;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

@RunWith(DataProviderRunner.class)
public class CreditCardValidationsImplTest {

    @InjectMocks
    CreditCardValidationsImpl validations;


    static CreateCardRequest validRequest = new CreateCardRequest();
    static CreateCardRequest emptyRequest = new CreateCardRequest();
    static CreateCardRequest outOfBoundRequest = new CreateCardRequest();
    static CreateCardRequest invalidCardRequest = new CreateCardRequest();
    static CreateCardRequest charactersRequest = new CreateCardRequest();

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
        validRequest.setCardNumber("4012888888881881");
        emptyRequest.setCardNumber("");
        outOfBoundRequest.setCardNumber("4012888888881881123456789109873");
        invalidCardRequest.setCardNumber("1111233333333");
        charactersRequest.setCardNumber("1973jdmsk097");
    }

    @DataProvider
    public static Object[][] validLengthRequests(){
        return new Object[][]{
                {validRequest, true},
                {emptyRequest, false},
                {outOfBoundRequest, false},
                {invalidCardRequest, true},
                {charactersRequest, true}
        };
    }

    @DataProvider
    public static Object[][] validCharactersRequests(){
        return new Object[][]{
                {validRequest, true},
                {emptyRequest, true},
                {outOfBoundRequest, true},
                {invalidCardRequest, true},
                {charactersRequest, false}
        };
    }

    @DataProvider
    public static Object[][] validLuhnRequests(){
        return new Object[][]{
                {validRequest, true},
                {emptyRequest, true},
                {outOfBoundRequest, false},
                {invalidCardRequest, false},
                {charactersRequest, false}
        };
    }

    @Test
    @UseDataProvider("validLengthRequests")
    public void test_validateLength(CreateCardRequest request, boolean expectedStatus){
        boolean result  = validations.isLengthValid(request.getCardNumber());
        assertEquals(expectedStatus, result);
    }

    @Test
    @UseDataProvider("validCharactersRequests")
    public void test_validateCharacters(CreateCardRequest request, boolean expectedStatus){
        boolean result  = validations.areCharactersVaild(request.getCardNumber());
        assertEquals(expectedStatus, result);
    }

    @Test
    @UseDataProvider("validLuhnRequests")
    public void test_validateLuhn(CreateCardRequest request, boolean expectedStatus){
        boolean result  = validations.isLuhnVaild(request.getCardNumber());
        assertEquals(expectedStatus, result);
    }

}
