package com.creditcard.processor.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EncryptRequest implements Serializable {
    @NonNull
    private String cardNumber;
}
