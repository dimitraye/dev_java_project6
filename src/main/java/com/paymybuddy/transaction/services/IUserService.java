package com.paymybuddy.transaction.services;

import com.paymybuddy.transaction.models.Transfer;
import com.paymybuddy.transaction.models.User;

import java.util.List;

public interface IUserService {

    public int getUserBalance(User user);


    public List<Transfer> getListTransactions();


}
