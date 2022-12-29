package com.paymybuddy.transaction.services;

import com.paymybuddy.transaction.models.Account;

/**
 * Interface that manage the interaction with the Account entity
 */
public interface IAccountService {

    /**
     * Save an account on the DB.
     * @param account
     * @return
     */
    Account save(Account account);
}
