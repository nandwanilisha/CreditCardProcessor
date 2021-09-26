package com.creditcard.processor.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "CREDIT_CARD_ACCOUNT")
public class CreditCardAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "CARD_NUMBER", unique = true)
    private String cardNumber;

    @Column(name = "BALANCE")
    private Long balance;
}
