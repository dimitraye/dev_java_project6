package com.paymybuddy.transaction.repositories;

import com.paymybuddy.transaction.models.Transfer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

  /**
   * Find every transfer made by an account
   * @param id of the account that send money
   * @return a list of tranfers
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
