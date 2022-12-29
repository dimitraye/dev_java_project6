package com.paymybuddy.transaction.repositories;

import com.paymybuddy.transaction.models.Transfer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Manage database operations for a transfer entity
 */
public interface TransferRepository extends JpaRepository<Transfer, Long> {

  /**
   * Find every transfer made by an account.
   * @param id of the account that send money.
   * @return a list of tranfer.
   */
  List<Transfer> findAllByAccountSenderId (Long id);

  /**
   * Find every transfer made by an account sender or toward an account receiver.
   * @param idSender
   * @param idReceiver
   * @return a list of transfer.
   */
  List<Transfer> findAllByAccountSenderIdOrAccountReceiverId (Long idSender, Long idReceiver);


}
