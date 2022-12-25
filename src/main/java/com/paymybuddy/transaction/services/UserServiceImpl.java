package com.paymybuddy.transaction.services;

import com.paymybuddy.transaction.models.Transfer;
import com.paymybuddy.transaction.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService{
    @Override
    public int getUserBalance(User user) {
        return 0;
    }

    @Override
    public List<Transfer> getListTransactions() {
        return null;
    }
}
