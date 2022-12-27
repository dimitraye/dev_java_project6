package com.paymybuddy.transaction.services;

import com.paymybuddy.transaction.models.Transfer;

import com.paymybuddy.transaction.repositories.TransferRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImpl implements ITransferService {
    @Autowired
    TransferRepository transferRepository;

    @Override
    public Transfer saveTransfert(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    @Override
    public List<Transfer> getListTransactions() {
        return null;
    }

    @Override
    public List<Transfer> findAllByAccountSenderId(Long id) {
        return transferRepository.findAllByAccountSenderId(id);
    }

    @Override
    public List<Transfer> findAllByAccountSenderIdOrAccountReceiverId(Long idSender,
                                                                      Long idReceiver) {
        return transferRepository.findAllByAccountSenderIdOrAccountReceiverId(idSender, idReceiver);
    }
}
