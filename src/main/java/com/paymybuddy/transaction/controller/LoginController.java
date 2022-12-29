package com.paymybuddy.transaction.controller;

import com.paymybuddy.transaction.models.User;
import com.paymybuddy.transaction.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Manage the requests linked to the login
 */
@Controller
public class LoginController {
    @Autowired
    IUserService userService;

    /**
     * Return the login  page
     * @param model
     * @param redirectAttrs
     * @return
     */
    @GetMapping("/login")
    public String login(Model model, RedirectAttributes redirectAttrs) {
        //Récupère le user courrant et cérifie si'il edt authentifié
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User user = userService.getUserDetails();
            model.addAttribute("user", user);
            redirectAttrs.addFlashAttribute("user", user);
            return "redirect:/index";
        }
        return "login";
    }

    /**
     * Manage the logout
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?error";
    }
}
