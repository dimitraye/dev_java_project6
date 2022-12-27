package com.paymybuddy.transaction.models;

import java.util.ArrayList;
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
    private double balance;

    @OneToMany(mappedBy = "accountSender")
    private List<Transfer> transfersSent = new ArrayList<>();

    @OneToMany(mappedBy = "accountReceiver")
    private List<Transfer> transfersReceived = new ArrayList<>();

    @OneToOne(mappedBy = "account")
    private User user;

}
