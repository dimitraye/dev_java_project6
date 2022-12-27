package com.paymybuddy.transaction.services;

import com.paymybuddy.transaction.models.Transfer;
import com.paymybuddy.transaction.models.User;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface IUserService {

    /**
     *
     * @param user
     * @return
     */
    User saveUser (User user);

    /**
     *
     * @param user
     * @return
     */
    int getUserBalance(User user);

    /**
     *
     * @return
     */
    List<Transfer> getListTransactions();


    /**
     *
     * @param user
     * @return
     */
    User getOauth2LoginInfo(Principal user);

    /**
     *
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
     *
     * @return
     */
    User getUserDetails ();

    /**
     *
     * @param id
     * @return
     */
    User findWithBuddiesAndAccountById(Long id);


    /**
     * 
     * @param username
     * @return
     */
    Optional<User> findByUsername (String username);


    User save(User user);
}
