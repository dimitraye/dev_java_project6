package com.paymybuddy.transaction.controller;

import com.paymybuddy.transaction.models.Account;
import com.paymybuddy.transaction.models.Transfer;
import com.paymybuddy.transaction.models.User;
import com.paymybuddy.transaction.services.IAccountService;
import com.paymybuddy.transaction.services.ITransferService;
import com.paymybuddy.transaction.services.IUserService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {
    @Autowired
    IUserService userService;

    @Autowired
    IAccountService accountService;

    @GetMapping("/addmoney")
    public String addMoneyOnAccount(@RequestParam double incomeamount) {
        User user = userService.getUserDetails();
        Account account = user.getAccount();

        account.setBalance(account.getBalance() + incomeamount);
        accountService.save(account);
        //return error if not possible

        return "redirect:/index";
    }

    @GetMapping("/removemoney")
    public String removeMoneyFromAccount(@RequestParam double outcomeamount) {
        User user = userService.getUserDetails();
        Account account = user.getAccount();

        if (outcomeamount <= account.getBalance()) {
            account.setBalance(account.getBalance() - outcomeamount);
        } else {
            // error message
        }
        accountService.save(account);
        //return error if not possible

        return "redirect:/index";
    }
}
