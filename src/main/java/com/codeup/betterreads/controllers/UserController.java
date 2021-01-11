package com.codeup.betterreads.controllers;

import com.codeup.betterreads.models.*;
import com.codeup.betterreads.repositories.*;
import com.codeup.betterreads.services.MailService;
import com.codeup.betterreads.services.MailgunService;
import kong.unirest.JsonNode;
import kong.unirest.UnirestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class UserController {
    private UserRepo userDao;
    private PasswordEncoder passwordEncoder;
    private BookshelfRepo bookshelfDao;
    private BookRepo bookDao;
    private ReviewRepo reviewDao;
    private ClubRepo clubDao;
    private ClubMemberRepo clubMemberDao;
    private MailService mailService;
    private MailgunService mgService;

    public UserController(UserRepo userDao, PasswordEncoder passwordEncoder, BookshelfRepo bookshelfDao, BookRepo bookDao, ReviewRepo reviewDao, ClubRepo clubDao, ClubMemberRepo clubMemberDao, MailService mailService, MailgunService mgService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.bookshelfDao = bookshelfDao;
        this.bookDao = bookDao;
        this.reviewDao = reviewDao;
        this.clubDao = clubDao;
        this.clubMemberDao = clubMemberDao;
        this.mailService = mailService;
        this.mgService = mgService;
    }

    @GetMapping("/sign-up")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/sign-up")
    public String createUser(@ModelAttribute User user, Model viewModel) {
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        Date currentDate = new Date();
        user.setCreatedDate(currentDate);
        String defaultIMG = "/img/logo.png";
        user.setAvatarURL(defaultIMG);
        User dbUser = userDao.save(user);

        // Send registration email
        try {
            JsonNode response = mgService.sendRegisterMessage(dbUser, true);
            System.out.println("response.toPrettyString() = " + response.toPrettyString());
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        viewModel.addAttribute("user", dbUser);
        return "redirect:/create-profile/" + dbUser.getUsername();
    }

    @GetMapping("/create-profile/{username}")
    public String showCreateProfile(Model viewModel, @PathVariable String username) {
        viewModel.addAttribute("user", userDao.findByUsername(username));
        return "user/create-profile";
    }

    @PostMapping("/create-profile/{username}")
    public String createProfile(@PathVariable String username, @ModelAttribute User userToBeUpdated) {
        User user = userDao.findByUsername(username);
        userToBeUpdated.setId(user.getId());
        userToBeUpdated.setUsername(user.getUsername());
        userToBeUpdated.setEmail(user.getEmail());
        userToBeUpdated.setPassword(user.getPassword());
        userToBeUpdated.setCreatedDate(user.getCreatedDate());
        User dbUser = userDao.save(userToBeUpdated);
        return "redirect:/profile/" + dbUser.getUsername();
    }

    @GetMapping("/edit-profile/{username}")
    public String showEditProfile(Model viewModel, @PathVariable String username) {
        viewModel.addAttribute("user", userDao.findByUsername(username));
        return "user/create-profile";
    }

    @PostMapping("/edit-profile/{username}")
    public String editProfile(@PathVariable String username, @ModelAttribute User userToBeUpdated) {
        User user = userDao.findByUsername(username);
        userToBeUpdated.setId(user.getId());
        userToBeUpdated.setUsername(user.getUsername());
        userToBeUpdated.setEmail(user.getEmail());
        userToBeUpdated.setPassword(user.getPassword());
        userToBeUpdated.setCreatedDate(user.getCreatedDate());
        User dbUser = userDao.save(userToBeUpdated);
        return "redirect:/profile/" + dbUser.getUsername();
    }

    @GetMapping("/profile/{username}")
    public String showUserProfile(Model viewModel, @PathVariable String username) {
        viewModel.addAttribute("user", userDao.findByUsername(username));

        User dbUser = userDao.findByUsername(username);
        List<Bookshelf> dbBookshelf = bookshelfDao.findAllByUserId(dbUser.getId());

        //looping through all clubs and retrieving users who are members of
        // clubs and placing them into an arraylist
        List<Club> dbClubs = clubDao.findAll();
        List<Club> clubsUserIsApartOf = new ArrayList<>();
        for(Club club : dbClubs) {
            if(clubMemberDao.findClubMemberByUserAndClub(dbUser, club) == null) {
                continue;
            }
            else {
                clubsUserIsApartOf.add(clubMemberDao.findClubMemberByUserAndClub(dbUser, club).getClub());
            }
        }

        ArrayList<Bookshelf> readList = new ArrayList<>();
        ArrayList<Bookshelf> readingList = new ArrayList<>();
        ArrayList<Bookshelf> wishlist = new ArrayList<>();

        for (Bookshelf book : dbBookshelf) {
            if (book.getBookShelfStatus() == BookshelfStatus.READ) {
                readList.add(book);
            } else if (book.getBookShelfStatus() == BookshelfStatus.READING) {
                readingList.add(book);
            } else if (book.getBookShelfStatus() == BookshelfStatus.WISHLIST) {
                wishlist.add(book);
            }
        }

        viewModel.addAttribute("read", readList);
        viewModel.addAttribute("reading", readingList);
        viewModel.addAttribute("wishlist", wishlist);
        viewModel.addAttribute("review", new Review());
        viewModel.addAttribute("bookclubs", clubsUserIsApartOf);

        return "user/profile-page";
    }

    @PostMapping("/profile/{username}/delete/{id}")
    public String deleteBook(@PathVariable String username, @PathVariable Bookshelf id) {
        User dbUser = userDao.findByUsername(username);
        bookshelfDao.deleteById(id.getId());
        return "redirect:/profile/" + dbUser.getUsername();
    }

    @PostMapping("/profile/{username}/{bookshelfId}")
    public String updateBookshelfStatus(
            @RequestParam(value = "bookshelfStatus") BookshelfStatus status,
            @PathVariable String username,
            @PathVariable long bookshelfId) {
        User dbUser = userDao.findByUsername(username);

        Bookshelf dbBookshelf = bookshelfDao.getOne(bookshelfId);
        dbBookshelf.setStatus(status);
        bookshelfDao.save(dbBookshelf);
        return "redirect:/profile/" + dbUser.getUsername();
    }

    @PostMapping("/profile/{username}/review/{bookId}")
    public String profileBookReview(
            @PathVariable String username,
            @PathVariable long bookId,
            @ModelAttribute Review dbReview
    ) {
        User dbUser = userDao.findByUsername(username);
        Book dbBook = bookDao.getOne(bookId);
        dbReview.setOwner(dbUser);
        dbReview.setBook(dbBook);

        reviewDao.save(dbReview);
        return "redirect:/profile/" + dbUser.getUsername();
    }

    @GetMapping("/review.json")
    public @ResponseBody
    List<Review> getReview() {
        return reviewDao.findAll();
    }

    @PostMapping("/profile/{username}/{bookId}/editReview/{reviewId}")
    public String editBookReview(
            @PathVariable String username,
            @PathVariable long bookId,
            @PathVariable long reviewId,
            @ModelAttribute Review reviewToBeEdited
    ) {
        User dbUser = userDao.findByUsername(username);
        Book dbBook = bookDao.getOne(bookId);
        Review extractedReview = reviewDao.getOne(reviewId);

        reviewToBeEdited.setId(reviewId);
        reviewToBeEdited.setOwner(dbUser);
        reviewToBeEdited.setBook(dbBook);
        reviewToBeEdited.setCreatedDate(extractedReview.getCreatedDate());
        reviewDao.save(reviewToBeEdited);

        return "redirect:/profile/" + dbUser.getUsername();
    }

    @PostMapping("/profile/{username}/deleteReview/{reviewId}")
    public String deleteProfileReview(
            @PathVariable String username,
            @PathVariable long reviewId
    ) {
        User dbUser = userDao.findByUsername(username);
        reviewDao.deleteById(reviewId);

        return "redirect:/profile/" + dbUser.getUsername();
    }

    @PostMapping("/profile/{username}/delete")
    public String deleteProfile(
            @PathVariable String username,
            RedirectAttributes redirectAttributes
    ) {
        User dbUser = userDao.findByUsername(username);
        List<Club> clubOwnerList = clubDao.findAllByOwnerId(dbUser.getId());

        if (clubOwnerList.isEmpty()) {
            userDao.deleteById(dbUser.getId());
            SecurityContextHolder.clearContext();
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("clubErr", true);
            return "redirect:/profile/" + dbUser.getUsername();
        }
    }
}
