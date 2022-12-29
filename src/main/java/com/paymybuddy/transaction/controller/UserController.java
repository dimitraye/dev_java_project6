package com.paymybuddy.transaction.controller;

import com.paymybuddy.transaction.models.Account;
import com.paymybuddy.transaction.models.Friendship;
import com.paymybuddy.transaction.models.Transfer;
import com.paymybuddy.transaction.models.User;
import com.paymybuddy.transaction.services.IFriendshipService;
import com.paymybuddy.transaction.services.ITransferService;
import com.paymybuddy.transaction.services.IUserService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Manage the requests linked to a user
 */
@Controller
public class UserController {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    IUserService userService;

    @Autowired
    ITransferService transferService;
    @Autowired
    private IFriendshipService friendshipService;


    /**
     *Manage the Index page
     * @param model
     * @return
     */
    //Home page
    @GetMapping(value={"", "/", "/index"})
    public String index(Model model) {
        User userInfo = (User) model.getAttribute("user");
        List<Transfer> transfers = new ArrayList<>();
        Long userAccountId = null;
        Double balance = 0.0;

        if (userInfo == null) {
            //Récupère l'utilisateur courrant
            userInfo = userService.getUserDetails();
        }

        userAccountId = userInfo.getAccount().getId();
        balance = userInfo.getAccount().getBalance();
        //Récupère tous les transfers liés à un user
        transfers = transferService.findAllByAccountSenderIdOrAccountReceiverId(userAccountId, userAccountId);
        model.addAttribute("transfers", transfers);
        model.addAttribute("userAccountId", userAccountId);
        model.addAttribute("balance", balance);

        //return the file index.html (load the page)
        return "index";
    }

    /**
     * Manage the user registration
     * @param user
     * @param redirAttrs
     * @return
     */
    @PostMapping("/adduser")
    public String addUser(User user, RedirectAttributes redirAttrs) {

        Account newAccount = new Account();
        user.setAccount(newAccount);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        User userFromDb = userService.findByUsername(user.getUsername()).orElse(null);

        if (userFromDb != null) {
            //Envoie un message sur la page de redirection
            redirAttrs.addFlashAttribute("error", "This email is already registered in our database");
            return "redirect:/userform";
        }

        try {
            userService.save(user);
            //Envoie un message sur la page de redirection
            redirAttrs.addFlashAttribute("success", "Your user account has been created successfully!");
        } catch (Exception e) {
            redirAttrs.addFlashAttribute("error", e.getMessage());
        }

        redirAttrs.addFlashAttribute("user", userFromDb);
        return "redirect:/login";
    }

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/userform")
    public String userForm(Model model) {
        model.addAttribute("user", new User());
        return "userform";
    }

    /**
     *
     * @param email
     * @param redirAttrs
     * @return
     */
    @GetMapping("/addbuddy")
    public String addBudy(@RequestParam String email, RedirectAttributes redirAttrs) {
        System.out.println("enter in add buddy");
        User user = userService.getUserDetails();
        user = userService.findWithFriendshipsAndAccountById(user.getId());

        // le compte existe
        if (user != null) {
            // find the connection/buddy
            User buddy = userService.findByUsername(email).orElse(null);

            if (buddy== null) {
                redirAttrs.addFlashAttribute("error", "This email is not registered in our database");
                return "redirect:/transfers";
            }
            if (user.getEmail().equals(email)) {
                redirAttrs.addFlashAttribute("error", "You can not add your own account as a buddy");
                return "redirect:/transfers";
            }

            List<User> friends = user.getFriendships().stream()
                .map(friendShip -> friendShip.getTargetUser())
                .collect(Collectors.toList());

            if (friends.contains(buddy)) {
                redirAttrs.addFlashAttribute("error", email + " is already your buddy");
                return "redirect:/transfers";
            }

            Friendship friendship = new Friendship(user, buddy);

            user.getFriendships().add(friendship);
            try {
                friendshipService.save(friendship);
                redirAttrs.addFlashAttribute("success", "The buddy has been added successfully!");
            } catch (Exception e) {
                redirAttrs.addFlashAttribute("error", e.getMessage());
            }

        }
        return "redirect:/transfers";
    }


    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/contact")
    public String contact(Model model) {
        User userInfo = (User) model.getAttribute("user");
        if (userInfo == null) {
            userInfo = userService.getUserDetails();
        }
        userInfo = userService.findWithFriendshipsAndAccountById(userInfo.getId());

        List<User> friends = userInfo.getFriendships().stream()
            .map(friendShip -> friendShip.getTargetUser())
            .collect(Collectors.toList());
        model.addAttribute("buddies", friends);

        return "contact";
    }


    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/profile")
    public String getProfile(Model model) {
        User userInfo = (User) model.getAttribute("user");
        if (userInfo == null) {// it means it is a direct click
            userInfo = userService.getUserDetails();
        }

        model.addAttribute("user", userInfo);

        return "profile";
    }


}
