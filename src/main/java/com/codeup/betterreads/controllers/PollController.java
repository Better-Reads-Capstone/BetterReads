package com.codeup.betterreads.controllers;

import com.codeup.betterreads.models.Club;
import com.codeup.betterreads.models.Poll;
import com.codeup.betterreads.models.User;
import com.codeup.betterreads.repositories.ClubRepo;
import com.codeup.betterreads.repositories.PollRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

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

    @PostMapping("/bookclub-poll/{id}")
    public String createPoll(
            @ModelAttribute Club club,
            @ModelAttribute Poll pollToBeCreated,
            @PathVariable long id
    ) {
        Date currentDate = new Date();
        User clubOwner = (clubDao.getOne(id)).getOwner();

        pollToBeCreated.setClub(club);
        pollToBeCreated.setUser(clubOwner);
        pollToBeCreated.setActive(true);
        pollToBeCreated.setCreatedDate(currentDate);

        Poll dbPoll = pollDao.save(pollToBeCreated);
        return "redirect:/show-poll/" + dbPoll.getId();
    }

    @GetMapping("/show-poll/{id}")
    public String showPoll(Model viewModel, @PathVariable long id) {
        viewModel.addAttribute("poll", pollDao.getOne(id));

        return "partials/show-poll";
    }
}
