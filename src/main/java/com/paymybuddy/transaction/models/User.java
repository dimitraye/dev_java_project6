package com.paymybuddy.transaction.models;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


/**
 * Model of the table User
 */
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
    // ...
    /**
     * the list of friendships asked by this user
     */
    @OneToMany(mappedBy = "sourceUser")
    private List<Friendship> friendships = new ArrayList<Friendship>();
    @OneToOne(cascade = CascadeType.ALL)
    private Account account;

    //Those 3 properties are used for Spring Security
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
