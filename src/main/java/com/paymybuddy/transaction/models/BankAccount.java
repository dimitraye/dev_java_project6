package com.paymybuddy.transaction.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int balance;

    @OneToMany(mappedBy = "accountSender")
    private List<Transaction> transactionsSender;

    @OneToMany(mappedBy = "accountReceiver")
    private List<Transaction> transactionsReceiver;

    @OneToOne(mappedBy = "bankAccount")
    private UserAccount userAccount;

}
