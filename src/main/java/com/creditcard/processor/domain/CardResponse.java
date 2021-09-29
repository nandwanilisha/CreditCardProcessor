package com.creditcard.processor.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse implements Serializable {
    private String name;
    private String cardNumber;
    private Long balance;
    private Long limit;
}
