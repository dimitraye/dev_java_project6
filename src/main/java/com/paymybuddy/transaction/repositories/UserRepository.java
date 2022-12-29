package com.paymybuddy.transaction.repositories;

import com.paymybuddy.transaction.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Manage database operations for a User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Find a user by its username.
   * @param username
   * @return the user.
   */
  Optional<User> findByUsername (String username);


  /**
   * Find the buddies of a user by their account's Id.
   * @param id
   * @return the buddies.
   */
  User findWithBuddiesAndAccountById(Long id);



}
