package com.paymybuddy.transaction.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class HomeController {

    //endpoints
    //Home
    @RequestMapping("/home")
    @RolesAllowed("USER")
    public String getGithub() {
        return "Welcome, GithubUser !";
    }



    //Money left on the account


    //History of all the transactions that have been made



}
