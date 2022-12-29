package com.paymybuddy.transaction.services;

import com.paymybuddy.transaction.models.Transfer;

import java.util.List;

/**
 * Interface that manage the interaction with the Transfer entity
 */
public interface ITransferService {

    /**
     *
     * @param newTransfer
     * @return
     */
    Transfer executeTransfer(Transfer newTransfer);
    /**
     *Save a transfer in the DB
     * @param transfer
     * @return
     */
    Transfer saveTransfert(Transfer transfer);


    /**
     * Get a list of all the transactions from the DB
     * @return a list.
     */
    List<Transfer> getListTransactions();

    /**
     * Find every transfer made by an account sender
     * @param id
     * @return a list of transfer.
     */
    List<Transfer> findAllByAccountSenderId (Long id);

    /**
     * Find every transfer made by an account sender or account receiver
     * @param idSender
     * @param idReceiver
     * @return a list of transfer.
     */
    List<Transfer> findAllByAccountSenderIdOrAccountReceiverId (Long idSender, Long idReceiver);



}
