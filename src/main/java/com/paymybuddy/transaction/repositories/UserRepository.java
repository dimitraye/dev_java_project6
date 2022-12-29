package com.paymybuddy.transaction.repositories;

import com.paymybuddy.transaction.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Manage database operations for a User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Find a user by its username(email).
   * @param username
   * @return the user.
   */
  Optional<User> findByUsername (String username);


  /**
   * Find the user with his buddies and his account by searching its Id.
   * @param id
   * @return the buddies.
   */
  User findWithFriendshipsAndAccountById(Long id);



}
