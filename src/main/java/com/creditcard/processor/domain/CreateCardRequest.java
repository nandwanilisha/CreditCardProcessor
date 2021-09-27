package com.creditcard.processor.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class CreateCardRequest implements Serializable {
    private String encryptedCardNumber;
    private Long balance;
}
