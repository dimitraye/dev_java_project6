package com.paymybuddy.transaction.services;

import com.paymybuddy.transaction.models.SecurityUser;
import com.paymybuddy.transaction.models.User;
import com.paymybuddy.transaction.repositories.UserRepository;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    UserRepository userRepository;


    @Override
    public User getOauth2LoginInfo(Principal user) {
        User userInfo = new User();
        OAuth2User principal =  ((OAuth2AuthenticationToken) user).getPrincipal();
        String principalName = principal.getName();
        OidcIdToken idToken = getIdToken(principal);
        if (idToken != null) {

            Map<String, Object> claims = idToken.getClaims();
            String firstName = (String) claims.get("given_name");
            String lastName = (String) claims.get("family_name");
            String email = (String) claims.get("email");

            userInfo.setExternalUserId(principalName);
            userInfo.setFirstName(firstName);
            userInfo.setLastName(lastName);
            userInfo.setEmail(email);

        }
        return userInfo;
    }

    @Override
    public OidcIdToken getIdToken(OAuth2User principal) {
        if (principal instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
            return oidcUser.getIdToken();
        }
        return null;
    }

    @Override
    public User getUserInfo(Principal user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userInfo = new User();
        // The user is a user from spring security
        if (auth.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            String userName = auth.getName();
            userInfo.setExternalUserId(userName);
        } else {
            userInfo = getOauth2LoginInfo(user);
        }
        return userInfo;
    }

    @Override
    public User getUserDetails () {
        SecurityUser userDetails = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            userDetails = (SecurityUser) auth.getPrincipal();
        }
        User user = userDetails.getUser();
        return user;
    }

    @Override
    public User findWithFriendshipsAndAccountById(Long id) {
        return userRepository.findWithFriendshipsAndAccountById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }


}
