package com.paymybuddy.transaction.services;

import com.paymybuddy.transaction.models.User;
import java.security.Principal;
import java.util.Optional;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * Interface that manage the interaction with the User entity
 */
public interface IUserService {


    /**
     *
     * @param user
     * @return
     */
    User getOauth2LoginInfo(Principal user);

    /**
     * Get the idToken of the user
     * @param principal
     * @return
     */
    OidcIdToken getIdToken(OAuth2User principal);

    /**
     *
     * @param user
     * @return
     */
    User getUserInfo (Principal user);


    /**
     * Get the details of the user.
     * @return the user's details.
     */
    User getUserDetails ();

    /**
     * Find the user's buddies by their account's Id
     * @param id
     * @return
     */
    User findWithFriendshipsAndAccountById(Long id);


    /**
     * Find a user by its username.
     * @param username
     * @return a user.
     */
    Optional<User> findByUsername (String username);


    /**
     * Save a user in the DB
     * @param user
     * @return
     */
    User save(User user);
}
