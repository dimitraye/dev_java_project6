package com.paymybuddy.transaction.services;

import com.paymybuddy.transaction.models.Account;

public interface IAccountService {
    public int addMoneyOnTheAppAccount ();

    public  int takeMoneyFromTheAppAccount();

    Account save(Account account);
}
