# CreditCardProcessor
This is a Spring Boot Application developed in JAVA, is used to add credt-card accounts and get all accounts. The Application exposes two REST API's which are described below:

1. API: /accounts/credit-card 
   This is a POST api which is used to create a new Credit Card Account.
   Consumes: JSON
   Payload:  
            {  
               "cardNumber": "XXXXXXXXXXXXXXXX",  
               "balance" : Z  
            }    
            where X is a number from 0-9, Z is the amount for initial balance.  
            Balance is optional as intial balance can be 0 at the begining.
            
   Produces: JSON  
             {   
               "message": "Y"  
             }   
             where Y is a message.  
    
    Validations:  
    1. Credit Card Number can only have digits.  
    2. The minimum balance in the account is 0.  
    3. Credit card number can have maximum 19 digits.  
    
2. API: /accounts/credit-cards  
   This is a GET api which is used to get all the Credit Card Accounts.  
   Produces: JSON   
   List of Credit Card Accounts:  
            [  
                {  
                    "cardNumber": "XXXXXXXXXXXXXXX",  
                    "balance": Z   
                },  
                {  
                    "cardNumber": "XXXXXXXXXXXXXXX",  
                    "balance": Z  
                }  
            ]  
  where X is a number from 0-9, Z is the amount for initial balance.
