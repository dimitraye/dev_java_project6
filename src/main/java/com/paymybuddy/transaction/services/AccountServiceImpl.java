package com.paymybuddy.transaction.services;

import com.paymybuddy.transaction.models.Account;
import com.paymybuddy.transaction.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements IAccountService{
    @Autowired
    AccountRepository accountRepository;

    @Override
    public int addMoneyOnTheAppAccount() {
        return 0;
    }

    @Override
    public int takeMoneyFromTheAppAccount() {
        return 0;
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }
}
