# CreditCardProcessor
This is a Spring Boot Application developed in JAVA, which is used to add credt-card accounts and get all accounts. The Application exposes two REST API's which are described below:

1. API: /accounts/credit-card 
   This is a POST api which is used to create a new Credit Card Account.
   Consumes: JSON
   Payload:  
            {  
               "name" : "AAAA"
               "cardNumber": "XXXXXXXXXXXXXXXX",  
               "limit" : Z  
            }    
            where A is alphabets of name, X is an alphanumeric characters of encrypted card number, Z is the digits of initial balance.  
            Intial balance will be be 0 at the begining.
            
   Produces: JSON  
             {   
               "message": "Y"  
             }   
             where Y is a message.  
    
    Validations:  
    1. Request must have name, card number and limit. If violated, Bad Request.
    1. Credit Card Number can only have digits. If violated, BadRequest.  
    2. The minimum balance in the account is 0. It will be taken 0 by default.
    3. Credit card number can have maximum 19 digits. If violated, BadRequest.  
    4. Credit card number will be validated against Luhn algorithm. If violated, BadRequest.
    
2. API: /accounts/credit-cards  
   This is a GET api which is used to get all the Credit Card Accounts.  
   Produces: JSON   
   List of Credit Card Accounts:  
            [  
                {  
                    "name": "AAAAAAA",  
                    "cardNumber": "XXXXXXXXXXXXXXX",  
                    "balance": Y,   
                    "limit": ZZZZZZ  
                },  
                {  
                    "name": "AAAAAAA",  
                    "cardNumber": "XXXXXXXXXXXXXXX",  
                    "balance": Y,   
                    "limit": ZZZZZZ  
                }  
            ]  
  where A is alphabets of name, X is an alphanumeric characters of encrypted card number, Z is the digits of initial balance, Y is the digits of balance.  
  
3. Helper API: accounts/credit-card/encrypt-helper Added just to get the encrypted credit card to be used for above API's  
   This is a Get API  
   Consumes: Json  
            {  
              "cardNumber": "XXXXXXXXXXX" 
            }  
   Produces: Json  
            {  
              "encryptedCardNumber": "YYYYYYYYY"  
            }  
   where  X is 0-9, Y is any charaters.
