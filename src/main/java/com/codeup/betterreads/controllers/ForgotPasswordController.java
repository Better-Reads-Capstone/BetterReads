package com.codeup.betterreads.controllers;


import com.codeup.betterreads.PasswordResetClasses.Utility;
import com.codeup.betterreads.models.User;
import com.codeup.betterreads.repositories.UserRepo;
import com.codeup.betterreads.services.MailgunService;
import com.codeup.betterreads.services.UserService;

import kong.unirest.JsonNode;
import net.bytebuddy.utility.RandomString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ForgotPasswordController {
    //CHECK
    @Autowired
    private MailgunService mailSender;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userDao;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "user/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset-password?token=" + token;

            JsonNode response = mailSender.sendPasswordResetMessage(userDao.findUserByEmail(email), resetPasswordLink, false);
            System.out.println("response.toPrettyString() = " + response.toPrettyString());
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        }
        return "user/forgot-password";
    }


    @GetMapping("/reset-password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = userService.getByResetPasswordToken(token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "user/reset-password";
        }
        else {
            model.addAttribute("token", token);
            return "user/reset-password";
        }
    }

    @PostMapping("/reset-password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            userService.updatePassword(user, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "user/reset-confirm";
    }
}
