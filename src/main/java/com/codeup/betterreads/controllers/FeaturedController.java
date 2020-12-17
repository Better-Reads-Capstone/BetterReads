package com.codeup.betterreads.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeaturedController {

    @GetMapping("/best-sellers")
    public String showBestSellers() {
        return "books/featured";
    }
}
