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
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int balance;

    @OneToMany
    private List<Transfer> transactions;

    @OneToOne(mappedBy = "account")
    private User user;

}
