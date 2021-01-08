package com.codeup.betterreads.controllers;

import com.codeup.betterreads.models.Club;
import com.codeup.betterreads.models.ClubMember;
import com.codeup.betterreads.models.Genre;
import com.codeup.betterreads.models.User;
import com.codeup.betterreads.repositories.ClubMemberRepo;
import com.codeup.betterreads.repositories.ClubRepo;
import com.codeup.betterreads.repositories.GenreRepo;
import com.codeup.betterreads.repositories.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import java.util.Date;
import java.util.List;

@Controller
public class ClubController {

    private UserRepo userDao;
    private ClubRepo clubDao;
    private ClubMemberRepo clubMemberDao;
    private GenreRepo genreDao;

    public ClubController(UserRepo userDao, ClubRepo clubDao, ClubMemberRepo clubMemberDao, GenreRepo genreDao) {
        this.userDao = userDao;
        this.clubDao = clubDao;
        this.clubMemberDao = clubMemberDao;
        this.genreDao = genreDao;
    }

    // Create Club
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
        clubMemberDao.save(clubMember);

        return "redirect:/bookclub/" + dbClub.getId();
    }

    // View Club Page
    @GetMapping("/bookclub/{id}")
    public String showBookClub(
            Model viewModel,
            @PathVariable long id,
            @ModelAttribute Club club) {
        viewModel.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        viewModel.addAttribute("club", clubDao.getOne(id));
        viewModel.addAttribute("members", clubMemberDao.findAllByClub(club));

        // For the conditional in the bookclub template; prevents users from joining a club multiple times!
        User clubUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ClubMember clubMember = clubMemberDao.findClubMemberByUserAndClub(clubUser, club);
        viewModel.addAttribute("member", clubMember);

        return "user/bookclub";
    }

    //Join Club
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

    //Leave Club
    @PostMapping("/bookclub/{id}/leave")
    public String leaveBookClub(@PathVariable long id) {
        User clubUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Club club = clubDao.getOne(id);
        ClubMember clubMember = clubMemberDao.findClubMemberByUserAndClub(clubUser, club);

        clubMemberDao.deleteById(clubMember.getId());

        return "redirect:/bookclub/" + id;
    }

    //Edit Club Page
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

    @GetMapping("/bookclubs")
    public String showBookclubs(Model viewModel) {
        List<Club> bookclubs = clubDao.findAll();
        viewModel.addAttribute("bookclubs", bookclubs);
        return "user/all-bookclubs";
    }

    @PostMapping("/bookclub/search")
    public String bookclubSearch(){
        //conditional that first takes the passed term and gets genre by name
        //if query == Genre name then get genre id and pass into clubDao.findByNameOrGenreIsLike
//        System.out.println(query);
//        query = "%"+query+"%";

//        Genre dbGenre = genreDao.findByNameIsLike(query);
//        System.out.println(dbGenre.getName());
//        List<Club> retrievedClubs = clubDao.findByNameOrGenreIsLike(query, query);
//        viewModel.addAttribute("bookclubs", retrievedClubs);
        return "redirect:/bookclubs";
    }

}
