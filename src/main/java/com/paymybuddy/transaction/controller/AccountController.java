package com.paymybuddy.transaction.controller;

import com.paymybuddy.transaction.models.Account;
import com.paymybuddy.transaction.models.User;
import com.paymybuddy.transaction.services.IAccountService;
import com.paymybuddy.transaction.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccountController {
    @Autowired
    IUserService userService;

    @Autowired
    IAccountService accountService;

    @GetMapping("/addmoney")
    public String addMoneyOnAccount(@RequestParam double incomeamount, RedirectAttributes redirAttrs) {
        User user = userService.getUserDetails();
        Account account = user.getAccount();

        account.setBalance(account.getBalance() + incomeamount);

        try {
            accountService.save(account);
            redirAttrs.addFlashAttribute("success", "The money has been added successfully!");
        } catch (Exception e) {
            redirAttrs.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/index";
    }

    @GetMapping("/removemoney")
    public String removeMoneyFromAccount(@RequestParam double outcomeamount, RedirectAttributes redirAttrs) {
        User user = userService.getUserDetails();
        Account account = user.getAccount();

        if (outcomeamount <= account.getBalance()) {
            account.setBalance(account.getBalance() - outcomeamount);
        } else {
            redirAttrs.addFlashAttribute("error", "The amount you want to remove is greater than your current balance");
            return "redirect:/index";
        }

        try {
            accountService.save(account);
            redirAttrs.addFlashAttribute("success", "The money has been removed successfully!");
        } catch (Exception e) {
            redirAttrs.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/index";
    }
}