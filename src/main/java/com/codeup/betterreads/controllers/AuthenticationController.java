package com.codeup.betterreads.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {
    @GetMapping("/login")
    public String showLoginForm() {
        // TODO placeholder path for the login form
        return "user/login";
    }

//    @GetMapping("/logout")
//    public String showLogoutForm() {
//        // TODO placeholder path for the logout form
//        return "users/logout";
//    }

}
