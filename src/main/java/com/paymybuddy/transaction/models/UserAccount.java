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
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String externalUserId;
    @OneToMany
    private List<UserAccount> buddies;
    @OneToOne(cascade = CascadeType.ALL)
    private BankAccount bankAccount;

}
