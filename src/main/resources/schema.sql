create table if not exists CREDIT_CARD_ACCOUNT(
    ID INT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR,
    CARD_NUMBER VARCHAR(19) unique,
    BALANCE INT,
    LIMIT_AMOUNT INT
);