package com.codeup.betterreads.controllers;

import com.codeup.betterreads.models.Book;
import com.codeup.betterreads.models.Bookshelf;
import com.codeup.betterreads.models.BookshelfStatus;
import com.codeup.betterreads.models.User;
import com.codeup.betterreads.repositories.BookRepo;
import com.codeup.betterreads.repositories.BookshelfRepo;
import com.codeup.betterreads.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Controller
public class UserController {
    private UserRepo userDao;
    private PasswordEncoder passwordEncoder;
    private BookshelfRepo bookshelfDao;
    private BookRepo bookDao;
//    @Value("${googleBooksAPI}")
//    private String googleBooksApi;

    public UserController(UserRepo userDao, PasswordEncoder passwordEncoder, BookshelfRepo bookshelfDao, BookRepo bookDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.bookshelfDao = bookshelfDao;
        this.bookDao = bookDao;
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

        ArrayList<Bookshelf> readList = new ArrayList<>();
        ArrayList<Bookshelf> readingList = new ArrayList<>();
        ArrayList<Bookshelf> wishlist = new ArrayList<>();

        for(Bookshelf book : dbBookshelf) {
            if(book.getBookShelfStatus() == BookshelfStatus.READ) {
                readList.add(book);
            }
            else if(book.getBookShelfStatus() == BookshelfStatus.READING) {
                readingList.add(book);
            }
            else if(book.getBookShelfStatus() == BookshelfStatus.WISHLIST) {
                wishlist.add(book);
            }
        }

        viewModel.addAttribute("read", readList);
        viewModel.addAttribute("reading", readingList);
        viewModel.addAttribute("wishlist", wishlist);
        return "user/profile-page";
    }

    @PostMapping("/profile/{username}/delete/{id}")
    public String deleteBook(@PathVariable String username, @PathVariable Bookshelf id) {
        User dbUser = userDao.findByUsername(username);
        bookshelfDao.deleteById(id.getId());
        return "redirect:/profile/" + dbUser.getUsername();
    }

}
