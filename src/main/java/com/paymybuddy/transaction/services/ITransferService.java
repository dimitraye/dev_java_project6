package com.paymybuddy.transaction.services;

import com.paymybuddy.transaction.models.Transfer;

import java.util.List;

public interface ITransferService {

    /**
     *
     * @param transfer
     * @return
     */
    Transfer saveTransfert(Transfer transfer);


    /**
     *
     * @return
     */
    List<Transfer> getListTransactions();

    /**
     *
     * @param id
     * @return
     */
    List<Transfer> findAllByAccountSenderId (Long id);

    /**
     *
     * @param idSender
     * @param idReceiver
     * @return
     */
    List<Transfer> findAllByAccountSenderIdOrAccountReceiverId (Long idSender, Long idReceiver);



}
