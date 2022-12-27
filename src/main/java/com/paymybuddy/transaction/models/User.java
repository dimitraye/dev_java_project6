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
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String externalUserId;
    @OneToMany
    private List<User> buddies = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    private Account account;

    private String username;
    private String password;
    private String roles;




    public String getEmail() {
        return username;
    }

    public void setEmail(String email) {
        this.username = email;
    }

}
