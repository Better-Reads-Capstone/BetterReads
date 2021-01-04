package com.codeup.betterreads.controllers;

import com.codeup.betterreads.models.Book;
import com.codeup.betterreads.models.Review;
import com.codeup.betterreads.models.User;
import com.codeup.betterreads.repositories.BookRepo;
import com.codeup.betterreads.repositories.ReviewRepo;
import com.codeup.betterreads.repositories.UserRepo;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {

    private BookRepo bookDao;
    private UserRepo userDao;
    private ReviewRepo reviewDao;

    public BookController(BookRepo bookDao, UserRepo userDao, ReviewRepo reviewDao) {
        this.bookDao = bookDao;
        this.userDao = userDao;
        this.reviewDao = reviewDao;
    }

    @GetMapping("/booksearch")
    public String searchResults(
            @RequestParam(name = "searchvalue") String searchvalue,
            Model viewModel) {

        viewModel.addAttribute("searchvalue", searchvalue);
        return "books/results";
    }

    @GetMapping("/book/{gbreference}")
    public String createBook(@PathVariable String gbreference, Model viewModel) {
        Book newBook = bookDao.findBookByGbreferenceEquals(gbreference);
        Book newDbBook = new Book();

        if (newBook == null){
            newDbBook.setGbreference(gbreference);
            newBook = bookDao.save(newDbBook);
        }
        //Should get all reviews by the created books id
        List<Review> dbReviews = reviewDao.findAllByBookId(newBook.getId());
        //places a new review object to review form and places all reviews retrieved to view
        viewModel.addAttribute("review", new Review());
        viewModel.addAttribute("reviews", dbReviews);
        viewModel.addAttribute("book", newBook);
        return "books/viewbook";
    }

    //will need to test these two PostMappings After Levi completes viewbook
    @PostMapping("/book/{bookId}/createReview/{username}")
    public String createReview(@PathVariable long bookId,
                               @PathVariable String username,
                               @ModelAttribute Review dbReview
    ){
        User dbUser = userDao.findByUsername(username);
        Book dbBook = bookDao.getOne(bookId);
        dbReview.setOwner(dbUser);
        dbReview.setBook(dbBook);
        reviewDao.save(dbReview);
        //may need to change to redirect to the viewedBook
        return "redirect:/books/viewbook";
    }

    @PostMapping("/{username}/{book}/editReview/{reviewId}")
    public String editReview(@PathVariable String username,
                             @PathVariable Book book,
                             @PathVariable long reviewId,
                             @ModelAttribute Review reviewToBeEdited
    ){
        User dbUser = userDao.findByUsername(username);
        Review extractedReview = reviewDao.getOne(reviewId);

        reviewToBeEdited.setOwner(dbUser);
        reviewToBeEdited.setBook(book);
        reviewToBeEdited.setId(reviewId);
        reviewToBeEdited.setCreatedDate(extractedReview.getCreatedDate());
        reviewDao.save(reviewToBeEdited);

        //may need to change to redirect to the viewedBook
        return "redirect:/books/viewbook";
    }

}