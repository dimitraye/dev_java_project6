package com.paymybuddy.transaction.controller;

import com.paymybuddy.transaction.models.Account;
import com.paymybuddy.transaction.models.Transfer;
import com.paymybuddy.transaction.models.User;
import com.paymybuddy.transaction.services.IAccountService;
import com.paymybuddy.transaction.services.ITransferService;
import com.paymybuddy.transaction.services.IUserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TransferController {
    @Autowired
    IUserService userService;

    @Autowired
    ITransferService transferService;

    @Autowired
    IAccountService accountService;

    @GetMapping("/transfers")
    public String getTransfers(Model model) {
        User user = userService.getUserDetails();
        user = userService.findWithBuddiesAndAccountById(user.getId());

        List<Transfer> transfers = new ArrayList<>();
        Long accountId = user.getAccount().getId();
        if (accountId == null) {
            //return "error message";
        }
        transferService.findAllByAccountSenderId(accountId).forEach(transfers::add);

        model.addAttribute("user", user);
        model.addAttribute("transfers", transfers);
       /* User newUser = new User();
        model.addAttribute("newUser", newUser);*/
        model.addAttribute("buddies", user.getBuddies());
        model.addAttribute("newTransfer", new Transfer());

        return "transfer";
    }

    @PostMapping("/addtransfer")
    @Transactional
    public String addTransfer(Transfer newTransfer, RedirectAttributes redirAttrs) {
        System.out.println("enter in add transfer");
        // check if there is no missing info
        if (newTransfer == null) {
            redirAttrs.addFlashAttribute("error", "The data is empty");
            return "redirect:/transfers";
        }
        if (newTransfer.getAccountReceiver() == null) {
            redirAttrs.addFlashAttribute("error", "The account receiver is empty");
            return "redirect:/transfers";
        }

        User userInfo = userService.getUserDetails();

        // le compte existe
        if (userInfo == null) {
            redirAttrs.addFlashAttribute("error", "An error occured while trying to access your user account");
            return "redirect:/transfers";
        }
        Account account = userInfo.getAccount();
        double amountToTransfer = newTransfer.getAmount() - (newTransfer.getAmount() * 0.005);

        // complete transfer data
        newTransfer.setAmount(amountToTransfer);
        newTransfer.setAccountSender(userInfo.getAccount());
        newTransfer.setDate(new Date());
        String description = StringUtils.hasText(newTransfer.getDescription()) ?
            newTransfer.getDescription() : "uncategorized";
        newTransfer.setDescription(description);

        // update the account balance
        account.setBalance(account.getBalance() + amountToTransfer);

        try {
            transferService.saveTransfert(newTransfer);
            accountService.save(account);
            redirAttrs.addFlashAttribute("success", "The transfer has been executed successfully!");
        } catch (Exception e) {
            redirAttrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/transfers";
    }
}