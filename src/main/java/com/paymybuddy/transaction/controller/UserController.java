package com.paymybuddy.transaction.controller;

import com.paymybuddy.transaction.models.Account;
import com.paymybuddy.transaction.models.Transfer;
import com.paymybuddy.transaction.models.User;
import com.paymybuddy.transaction.services.ITransferService;
import com.paymybuddy.transaction.services.IUserService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    IUserService userService;

    @Autowired
    ITransferService transferService;
    //endpoints
    //Home
    @RequestMapping("/home")
    @RolesAllowed("USER")
    public String getGithub() {
        return "Welcome, GithubUser !";
    }



    //Money left on the account
    //Page d'accueil
    @GetMapping("/index")
    public String index(Model model) {
        User userInfo = (User) model.getAttribute("user");
        List<Transfer> transfers = new ArrayList<>();
        Long userAccountId = null;
        Double balance = 0.0;

        if (userInfo == null) {// it means it is a direct click
            userInfo = userService.getUserDetails();
        }

        userAccountId = userInfo.getAccount().getId();
        balance = userInfo.getAccount().getBalance();
        transfers = transferService.findAllByAccountSenderIdOrAccountReceiverId(userAccountId, userAccountId);
        model.addAttribute("transfers", transfers);
        model.addAttribute("userAccountId", userAccountId);
        model.addAttribute("balance", balance);

        return "index";
    }


    @PostMapping("/_adduser")
    public String _addUser(User user, RedirectAttributes redirectAttrs) {

        Account newAccount = new Account();
        user.setAccount(newAccount);
        User userFromDb = userService.save(user);

        redirectAttrs.addFlashAttribute("user", userFromDb);
        return "redirect:/index";
    }

    @PostMapping("/adduser")
    public String addUser(User user, RedirectAttributes redirectAttrs) {

        Account newAccount = new Account();
        user.setAccount(newAccount);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        User userFromDb = userService.save(user);

        redirectAttrs.addFlashAttribute("user", userFromDb);
        return "redirect:/index";
    }

    @GetMapping("/userform")
    public String userForm(Model model) {
        model.addAttribute("user", new User());
        return "userform";
    }

    @GetMapping("/addbuddy")
    public String addBudy(@RequestParam String email, RedirectAttributes redirAttrs) {
        System.out.println("enter in add buddy");
        User userInfo = userService.getUserDetails();
        userInfo = userService.findWithBuddiesAndAccountById(userInfo.getId());

        // le compte existe
        if (userInfo != null) {
            // find the connection/buddy
            User buddy = userService.findByUsername(email).orElse(null);
            if (buddy != null
                && !userInfo.getEmail().equals(email)
            ) {
                userInfo.getBuddies().add(buddy);
            }
            try {
                userService.save(userInfo);
                redirAttrs.addFlashAttribute("success", "The buddy has been saved successfully!");
            } catch (Exception e) {
                redirAttrs.addAttribute("error", e.getMessage());
            }

        }
        return "redirect:/transfers";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        User userInfo = (User) model.getAttribute("user");
        if (userInfo == null) {// it means it is a direct click
            userInfo = userService.getUserDetails();
        }
        userInfo = userService.findWithBuddiesAndAccountById(userInfo.getId());

        model.addAttribute("buddies", userInfo.getBuddies());

        return "contact";
    }


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
