package com.paymybuddy.transaction.services;

import com.paymybuddy.transaction.models.Transfer;

import java.util.List;

public interface ITransferService {
    public List<Transfer> getListTransactions();

}
