package com.codeup.betterreads.controllers;

import com.codeup.betterreads.models.*;
import com.codeup.betterreads.repositories.ClubMemberRepo;
import com.codeup.betterreads.repositories.ClubRepo;
import com.codeup.betterreads.repositories.PostRepo;
import com.codeup.betterreads.repositories.UserRepo;
import com.codeup.betterreads.repositories.GenreRepo;
import com.codeup.betterreads.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class ClubController {

    private UserRepo userDao;
    private ClubRepo clubDao;
    private ClubMemberRepo clubMemberDao;
    private PostRepo postDao;
    private GenreRepo genreDao;

    @Autowired
    UserService usersSvc;

    public ClubController(UserRepo userDao, ClubRepo clubDao, ClubMemberRepo clubMemberDao, PostRepo postDao, GenreRepo genreDao) {
        this.userDao = userDao;
        this.clubDao = clubDao;
        this.clubMemberDao = clubMemberDao;
        this.postDao = postDao;
        this.genreDao = genreDao;
    }

    // Create Club
    @GetMapping("/create-club/{username}")
    public String showCreateClub(Model viewModel, @PathVariable String username) {
        List<Genre> genreList = genreDao.findAll();

        viewModel.addAttribute("user", userDao.findByUsername(username));
        viewModel.addAttribute("club", new Club());
        viewModel.addAttribute("genres", genreList);
        return "user/create-club";
    }

    @PostMapping("/create-club/{username}")
    public String createClub(@PathVariable String username,
                             @Valid Club club,
                             Errors validation,
                             @ModelAttribute User userToBeUpdated,
                             Model viewModel) {

        User user = userDao.findByUsername(username);
        Date currentDate = new Date();

        if (validation.hasErrors()) {
            viewModel.addAttribute("errors", validation);
            viewModel.addAttribute("club", club);
            return "user/create-club";
        }

        club.setOwner(user);
        club.setCreatedDate(currentDate);
        String defaultIMG = "/img/logo.png";
        club.setHeaderImageUrl(defaultIMG);

        Club dbClub = clubDao.save(club);

        ClubMember clubMember = new ClubMember();

        clubMember.setClub(dbClub);
        clubMember.setUser(user);
        clubMember.setIsAdmin(true);
        clubMemberDao.save(clubMember);

        return "redirect:/bookclub/" + dbClub.getId();
    }

    // View Club Page
    @GetMapping("/bookclub/{id}")
    public String showBookClub(
            Model viewModel,
            @PathVariable long id,
            @ModelAttribute Club club) {
        viewModel.addAttribute("user", usersSvc.loggedInUser());
        viewModel.addAttribute("club", clubDao.getOne(id));
        viewModel.addAttribute("members", clubMemberDao.findAllByClub(club));
        viewModel.addAttribute("posts", postDao.findAllByClub(club));

        // For the conditional in the bookclub template; prevents users from joining a club multiple times!
        User clubUser = usersSvc.loggedInUser();
        ClubMember clubMember = clubMemberDao.findClubMemberByUserAndClub(clubUser, club);
        viewModel.addAttribute("member", clubMember);

        return "user/bookclub";
    }

    //Join Club
    @PostMapping("/bookclub/{id}/join")
    public String joinBookClub(@PathVariable long id) {
        User clubUser = usersSvc.loggedInUser();
        ClubMember clubMember = new ClubMember();

        clubMember.setClub(clubDao.getOne(id));
        clubMember.setUser(clubUser);
        clubMember.setIsAdmin(false);

        clubMemberDao.save(clubMember);
        System.out.println(clubUser.getId());
        System.out.println(clubMember.getId());

        return "redirect:/bookclub/" + id;
    }

    //Leave Club
    @PostMapping("/bookclub/{id}/leave")
    public String leaveBookClub(@PathVariable long id) {
        User clubUser = usersSvc.loggedInUser();
        Club club = clubDao.getOne(id);
        ClubMember clubMember = clubMemberDao.findClubMemberByUserAndClub(clubUser, club);

        clubMemberDao.deleteById(clubMember.getId());

        return "redirect:/bookclub/" + id;
    }

    //Edit Club Page
    @GetMapping("/edit-bookclub/{id}")
    public String showEditBookClub (Model viewModel, @PathVariable long id) {
        User user = usersSvc.loggedInUser();
        Club club = clubDao.getOne(id);
        ClubMember clubMember = clubMemberDao.findClubMemberByUserAndClub(user, club);

        viewModel.addAttribute("club", club);
        viewModel.addAttribute("user", user);

        if(!usersSvc.isAdmin(clubMember)){
            return "redirect:/bookclub/" + id;
        }

        viewModel.addAttribute("club", clubDao.getOne(id));
        List<Genre> genreList = genreDao.findAll();
        viewModel.addAttribute("genres", genreList);

        return "user/create-club";
    }

    @PostMapping("/edit-bookclub/{id}")
    public String editProfile(@PathVariable long id, @ModelAttribute Club clubToBeUpdated) {
        Club club = clubDao.getOne(id);
        clubToBeUpdated.setOwner(club.getOwner());
        clubToBeUpdated.setCreatedDate(club.getCreatedDate());

        Club dbClub = clubDao.save(clubToBeUpdated);
        return "redirect:/bookclub/" + dbClub.getId();
    }

    //Delete club
    @RequestMapping(value = "/bookclub/{id}/delete", method = { RequestMethod.GET, RequestMethod.POST })
    public String deleteClub(@PathVariable long id) {
        Club club = clubDao.getOne(id);
        User user = usersSvc.loggedInUser();

        if(user != club.getOwner()){
            return "redirect:/bookclub/" + id;
        }

        clubDao.delete(club);

        return "redirect:/profile/" + user.getUsername();
    }

    //Assign Admin
    @PostMapping("/bookclub/{id}/admin/{userId}")
    public String makeAdmin(@PathVariable long id, @PathVariable long userId) {
        User user = userDao.getOne(userId);
        Club club = clubDao.getOne(id);
        ClubMember clubMember = clubMemberDao.findClubMemberByUserAndClub(user, club);

        clubMember.setIsAdmin(true);
        clubMemberDao.save(clubMember);

        return "redirect:/bookclub/" + id;
    }

    //Revoke Admin Status
    @PostMapping("/bookclub/{id}/member/{userId}")
    public String removeAdminStatus(@PathVariable long id, @PathVariable long userId) {
        User user = userDao.getOne(userId);
        Club club = clubDao.getOne(id);
        ClubMember clubMember = clubMemberDao.findClubMemberByUserAndClub(user, club);

        clubMember.setIsAdmin(false);
        clubMemberDao.save(clubMember);

        return "redirect:/bookclub/" + id;
    }
}