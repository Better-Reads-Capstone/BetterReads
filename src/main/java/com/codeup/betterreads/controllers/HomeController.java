package com.codeup.betterreads.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage() {return "index";}

    @GetMapping("/best-sellers")
    public String showBestSellers() {
        return "books/featured";
    }

    @GetMapping("/faq")
    public String showFAQ() { return "general/faq"; }

    @GetMapping("/about-us")
    public String showAboutUs() { return "general/about-us"; }

//    @GetMapping("/meet-the-devs")
//    public String showMeetDevs() { return "general/meet-devs"; }
}
