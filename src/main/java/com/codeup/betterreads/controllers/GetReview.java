package com.codeup.betterreads.controllers;

import com.codeup.betterreads.models.Book;
import com.codeup.betterreads.models.Review;
import com.codeup.betterreads.models.User;
import com.codeup.betterreads.repositories.BookRepo;
import com.codeup.betterreads.repositories.ReviewRepo;
import com.codeup.betterreads.repositories.UserRepo;
import org.springframework.web.bind.annotation.*;

@RestController
public class GetReview {

    private UserRepo userDao;
    private BookRepo bookDao;
    private ReviewRepo reviewDao;

    public GetReview(UserRepo userDao, BookRepo bookDao, ReviewRepo reviewDao) {
        this.userDao = userDao;
        this.bookDao = bookDao;
        this.reviewDao = reviewDao;
    }

    @GetMapping("/profile/{username}/editReview/{bookId}")
    public Review getReviewToEdit(@PathVariable String username, @PathVariable long bookId) {
        Book dbBook = bookDao.getOne(bookId);
        User dbUser = userDao.findByUsername(username);
        Review dbReview = reviewDao.findByOwnerIdAndBookId(dbUser.getId(), dbBook.getId());
        if(dbReview.getId() < 1) {
            return new Review();
        }
        else {
            return dbReview;
        }
    }
}



