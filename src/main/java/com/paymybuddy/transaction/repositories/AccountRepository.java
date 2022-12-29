package com.paymybuddy.transaction.repositories;

import com.paymybuddy.transaction.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Manage database operations for a Account entity
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

}
