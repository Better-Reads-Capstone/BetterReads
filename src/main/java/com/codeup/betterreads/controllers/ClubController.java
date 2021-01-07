package com.codeup.betterreads.controllers;

import com.codeup.betterreads.models.*;
import com.codeup.betterreads.repositories.ClubMemberRepo;
import com.codeup.betterreads.repositories.ClubRepo;
import com.codeup.betterreads.repositories.PostRepo;
import com.codeup.betterreads.repositories.UserRepo;
import com.codeup.betterreads.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import java.util.Date;

@Controller
public class ClubController {

    private UserRepo userDao;
    private ClubRepo clubDao;
    private ClubMemberRepo clubMemberDao;
    private PostRepo postDao;

    @Autowired
    UserService usersSvc;

    public ClubController(UserRepo userDao, ClubRepo clubDao, ClubMemberRepo clubMemberDao, PostRepo postDao) {
        this.userDao = userDao;
        this.clubDao = clubDao;
        this.clubMemberDao = clubMemberDao;
        this.postDao = postDao;
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

    //Create Post
    @GetMapping("/bookclub/{id}/create-post")
    public String viewCreatePostForm(Model viewModel, @PathVariable long id) {
        viewModel.addAttribute("post", new Post());
        viewModel.addAttribute("club", clubDao.getOne(id));
        return "user/create-post";
    }

    @PostMapping("/bookclub/{id}/create-post")
    public String createPost(
            @PathVariable long id,
            @ModelAttribute Club club,
            @ModelAttribute Post post) {

        Date currentDate = new Date();
        User user = usersSvc.loggedInUser();

        post.setClub(club);
        post.setUser(user);
        post.setCreatedDate(currentDate);
        post.setUpdatedDate(currentDate);

        Post dbPost = postDao.save(post);

        return "redirect:/bookclub/" + id + "/" + dbPost.getId();
    }

    @GetMapping("/bookclub/{id}/{postId}")
    public String viewPost(
            Model viewModel,
            @PathVariable long id,
            @PathVariable long postId) {

        viewModel.addAttribute("post", postDao.getOne(postId));
        viewModel.addAttribute("club", clubDao.getOne(id));
        User user = usersSvc.loggedInUser();
        ClubMember clubMember = clubMemberDao.findClubMemberByUserAndClub(user, clubDao.getOne(id));
        viewModel.addAttribute("member", clubMember);

        return "user/club-post";
    }

    @GetMapping("/bookclub/{id}/edit-post/{postId}")
    public String viewEditPostForm(
            Model viewModel,
            @PathVariable long id,
            @PathVariable long postId) {

        Post post = postDao.getOne(postId);

        if(!usersSvc.isOwner(post.getUser())){
            return "redirect:/bookclub/" + id + "/" + postId;
        }

        viewModel.addAttribute("post", postDao.getOne(postId));
        viewModel.addAttribute("club", clubDao.getOne(id));

        return "user/create-post";
    }

    @PostMapping("/bookclub/{id}/edit-post/{postId}")
    public String editPost(
            @ModelAttribute Post post,
            @ModelAttribute Club club,
            @PathVariable long id,
            @PathVariable long postId) {

        Post postToBeUpdated = postDao.getOne(postId);
        Date currentDate = new Date();

        postToBeUpdated.setUpdatedDate(currentDate);
        postToBeUpdated.setCreatedDate(post.getCreatedDate());
        postToBeUpdated.setUser(post.getUser());
        postToBeUpdated.setClub(post.getClub());

        Post dbPost = postDao.save(postToBeUpdated);
        return "redirect:/bookclub/" + id + "/" + dbPost.getId();
    }

    @RequestMapping(value = "/bookclub/{id}/delete-post/{postId}", method = { RequestMethod.GET, RequestMethod.POST })
    public String deletePost(@PathVariable long id, @PathVariable long postId) {
        Post post = postDao.getOne(postId);

        if(!usersSvc.isOwner(post.getUser())){
            return "redirect:/bookclub/" + id;
        }

        postDao.delete(post);

        return "redirect:/bookclub/" + id;
    }

    @PostMapping("/bookclub/{id}/admin/{userId}")
    public String makeAdmin(@PathVariable long id, @PathVariable long userId) {
        User user = userDao.getOne(userId);
        Club club = clubDao.getOne(id);
        ClubMember clubMember = clubMemberDao.findClubMemberByUserAndClub(user, club);

        clubMember.setIsAdmin(true);
        clubMemberDao.save(clubMember);

        return "redirect:/bookclub/" + id;
    }

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