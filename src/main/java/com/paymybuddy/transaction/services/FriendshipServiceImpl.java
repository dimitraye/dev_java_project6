package com.paymybuddy.transaction.services;

import com.paymybuddy.transaction.models.Friendship;
import com.paymybuddy.transaction.repositories.FriendshipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FriendshipServiceImpl implements IFriendshipService {
    @Autowired
    FriendshipRepository friendshipRepository;


    @Override
    public Friendship save(Friendship friendship) {
        return friendshipRepository.save(friendship);
    }

}
