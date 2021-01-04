package com.codeup.betterreads.controllers;

import com.codeup.betterreads.models.Club;
import com.codeup.betterreads.models.ClubMember;
import com.codeup.betterreads.models.Genre;
import com.codeup.betterreads.models.User;
import com.codeup.betterreads.repositories.ClubMemberRepo;
import com.codeup.betterreads.repositories.ClubRepo;
import com.codeup.betterreads.repositories.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.GeneratedValue;
import java.util.Date;

@Controller
public class ClubController {

    private UserRepo userDao;
    private ClubRepo clubDao;
    private ClubMemberRepo clubMemberDao;

    public ClubController(UserRepo userDao, ClubRepo clubDao, ClubMemberRepo clubMemberDao) {
        this.userDao = userDao;
        this.clubDao = clubDao;
        this.clubMemberDao = clubMemberDao;
    }

    @GetMapping("/create-club/{username}")
    public String showCreateClub(Model viewModel, @PathVariable String username) {
        viewModel.addAttribute("user", userDao.findByUsername(username));
        viewModel.addAttribute("club", new Club());
        return "user/create-club";
    }

    @PostMapping("/create-club/{username}")
    public String createClub(@PathVariable String username,
                             @ModelAttribute User userToBeUpdated,
                             @ModelAttribute Club club) {
        User user = userDao.findByUsername(username);
        Date currentDate = new Date();

        club.setOwner(user);
        club.setCreatedDate(currentDate);
        String defaultIMG = "/img/logo.png";
        club.setHeaderImageUrl(defaultIMG);

        Club dbClub = clubDao.save(club);

        ClubMember clubMember = new ClubMember();

        clubMember.setClub(dbClub);
        clubMember.setUser(user);
        clubMember.setIsAdmin(true);
        return "redirect:/bookclub/" + dbClub.getId();
    }

    @GetMapping("/bookclub/{id}")
    public String showBookClub(
            Model viewModel,
            @PathVariable long id,
            @ModelAttribute Club club) {
        viewModel.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        viewModel.addAttribute("club", clubDao.getOne(id));
        User clubUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ClubMember clubMember = clubMemberDao.findClubMemberByUser(clubUser);
        System.out.println("Club Member Obj: " + clubMember);
        System.out.println("User Obj: " + clubUser);
        System.out.println("Club Obj: " + club);

        viewModel.addAttribute("member", clubMember);

        return "user/bookclub";
    }

    @PostMapping("/bookclub/{id}/join")
    public String joinBookClub(@PathVariable long id) {
        User clubUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ClubMember clubMember = new ClubMember();

        clubMember.setClub(clubDao.getOne(id));
        clubMember.setUser(clubUser);
        clubMember.setIsAdmin(false);

        clubMemberDao.save(clubMember);
        System.out.println(clubUser.getId());
        System.out.println(clubMember.getId());
        return "redirect:/bookclub/" + id;
    }

    @GetMapping("/edit-bookclub/{id}")
    public String showEditBookClub (Model viewModel, @PathVariable long id) {
        viewModel.addAttribute("club", clubDao.getOne(id));
        return "user/edit-bookclub";
    }

    @PostMapping("/edit-bookclub/{id}")
    public String editProfile(@PathVariable long id, @ModelAttribute Club clubToBeUpdated) {
        Club club = clubDao.getOne(id);
        clubToBeUpdated.setOwner(club.getOwner());
        clubToBeUpdated.setCreatedDate(club.getCreatedDate());

        Club dbClub = clubDao.save(clubToBeUpdated);
        return "redirect:/bookclub/" + dbClub.getId();
    }

}
