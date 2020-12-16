package com.codeup.betterreads.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String homePage() {return "index";}

    @GetMapping("/booksearch")
    public String searchResults() {return "results";}
}
