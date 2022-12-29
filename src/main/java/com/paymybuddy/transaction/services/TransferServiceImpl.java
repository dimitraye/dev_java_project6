package com.paymybuddy.transaction.services;

import com.paymybuddy.transaction.models.Account;
import com.paymybuddy.transaction.models.Transfer;

import com.paymybuddy.transaction.repositories.AccountRepository;
import com.paymybuddy.transaction.repositories.TransferRepository;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class TransferServiceImpl implements ITransferService {
    @Autowired
    TransferRepository transferRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    @Transactional
    public Transfer executeTransfer(Transfer newTransfer) {

        Account accountReceiver = newTransfer.getAccountReceiver();
        Account accountSender = newTransfer.getAccountSender();

        double outcomeamount = newTransfer.getAmount();


        if (outcomeamount > accountSender.getBalance()) {
            log.debug("The transfer amount is greater than your balance");
            return null;
        }

        double commission = outcomeamount * 0.005;
        double amountToTransfer = outcomeamount - commission;
        // complete transfer data
        newTransfer.setAmount(amountToTransfer);
        newTransfer.setDate(new Date());
        String description = StringUtils.hasText(newTransfer.getDescription()) ?
            newTransfer.getDescription() : "uncategorized";
        newTransfer.setDescription(description);
        newTransfer.setCommission(commission);

        // update the account balance
        accountReceiver.setBalance(accountReceiver.getBalance() + amountToTransfer);
        accountSender.setBalance(accountSender.getBalance() - outcomeamount);

        // update the accounts and save the transfer in db
        try {
            accountRepository.save(accountReceiver);
            accountRepository.save(accountSender);
            log.info("Saving Transaction...");
            return transferRepository.save(newTransfer);
        } catch (Exception e) {
            log.error("Error while trying to execute transaction", e.getMessage());
        }
        return null;
    }

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
