package com.paymybuddy.transaction.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Friendship {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  /**
   * the user who asked the friendship
   */
  @ManyToOne()
  @JoinColumn(name = "user_id")
  private User sourceUser;

  /**
   * the user to whom the friendship was asked
   */
  @ManyToOne()
  @JoinColumn(name = "friend_id")
  private User targetUser;


  public Friendship(User sourceUser, User targetUser) {
    this.sourceUser = sourceUser;
    this.targetUser = targetUser;
  }

  public Friendship(User targetUser) {
    this.targetUser = targetUser;
  }
}
