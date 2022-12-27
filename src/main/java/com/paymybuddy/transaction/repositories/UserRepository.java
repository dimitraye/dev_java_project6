package com.paymybuddy.transaction.repositories;

import com.paymybuddy.transaction.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername (String username);


  User findByExternalUserId(String externalUserId);

  User findWithBuddiesAndAccountById(Long id);



}
