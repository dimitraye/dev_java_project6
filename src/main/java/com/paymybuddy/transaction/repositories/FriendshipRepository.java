package com.paymybuddy.transaction.repositories;

import com.paymybuddy.transaction.models.Friendship;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipRepository  extends JpaRepository<Friendship, Long> {
  /**
   * Find a friendship by source user Id.
   * @param id
   * @return the Friendship.
   */
  List<Friendship> findBySourceUserId (long id);
}
