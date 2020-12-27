package com.codeup.betterreads.controllers;

import com.codeup.betterreads.models.Poll;
import com.codeup.betterreads.repositories.ClubRepo;
import com.codeup.betterreads.repositories.PollRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PollController {

    private PollRepo pollDao;
    private ClubRepo clubDao;

    public PollController(PollRepo pollDao, ClubRepo clubDao) {
        this.pollDao = pollDao;
        this.clubDao = clubDao;
    }

    @GetMapping("/bookclub-poll/{id}")
    public String showCreatePoll(Model viewModel, @PathVariable long id) {
        viewModel.addAttribute("club", clubDao.getOne(id));
        viewModel.addAttribute("poll", new Poll());
        return "user/club-poll";
    }
}
