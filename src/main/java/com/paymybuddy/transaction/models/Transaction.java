package com.paymybuddy.transaction.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private BankAccount accountSender;
    @ManyToOne(fetch = FetchType.EAGER)
    private BankAccount accountReceiver;
    private Date date;
    private double amount;
    private String description;
}
