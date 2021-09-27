package com.creditcard.processor.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EncryptRequest implements Serializable {
    private String cardNumber;
}
