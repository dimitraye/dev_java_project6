package com.paymybuddy.transaction.controller;

import com.paymybuddy.transaction.models.Account;
import com.paymybuddy.transaction.models.Transfer;
import com.paymybuddy.transaction.models.User;
import com.paymybuddy.transaction.services.ITransferService;
import com.paymybuddy.transaction.services.IUserService;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Manage the requests linked to a transfer
 */
@Controller
public class TransferController {
    @Autowired
    IUserService userService;

    @Autowired
    ITransferService transferService;

    /**
     * Get the transfers
     * @param model
     * @return
     */
    @GetMapping("/transfers")
    public String getTransfers(Model model, RedirectAttributes redirAttrs) {
        User user = userService.getUserDetails();
        user = userService.findWithBuddiesAndAccountById(user.getId());

        List<Transfer> transfers = new ArrayList<>();
        Long accountId = user.getAccount().getId();
        if (accountId == null) {
            redirAttrs.addFlashAttribute("error", "An error occured while trying to access your account");
            return "redirect:/transfers";
        }
        transferService.findAllByAccountSenderId(accountId).forEach(transfers::add);

        model.addAttribute("user", user);
        model.addAttribute("transfers", transfers);
        model.addAttribute("buddies", user.getBuddies());
        model.addAttribute("newTransfer", new Transfer());
        //return the file transfer.html.
        return "transfer";
    }

    /**
     * Save a new transfer
     * @param newTransfer
     * @param redirAttrs
     * @return
     */
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
        Account accountSender = userInfo.getAccount();
        newTransfer.setAccountSender(accountSender);

        if (newTransfer.getAmount() > accountSender.getBalance()) {
            redirAttrs.addFlashAttribute("error", "The amount you want to send is greater than your current balance");
            return "redirect:/transfers";
        }

        Transfer transfer = transferService.executeTransfer(newTransfer);
        if (transfer != null) {
            redirAttrs.addFlashAttribute("success", "The transfer has been executed successfully!");
        } else {
            redirAttrs.addFlashAttribute("error", "an error occured while trying to transfer money to your buddy");
        }

        return "redirect:/transfers";
    }
}