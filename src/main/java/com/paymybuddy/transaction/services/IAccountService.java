package com.paymybuddy.transaction.services;

import com.paymybuddy.transaction.models.Transfer;
import com.paymybuddy.transaction.models.User;

import java.util.List;

public interface IAccountService {
    public int addMoneyOnTheAppAccount ();

    public  int takeMoneyFromTheAppAccount();
}
