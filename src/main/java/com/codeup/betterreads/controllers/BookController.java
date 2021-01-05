package com.codeup.betterreads.controllers;

import com.codeup.betterreads.models.*;
import com.codeup.betterreads.repositories.BookRepo;
import com.codeup.betterreads.repositories.BookshelfRepo;
import com.codeup.betterreads.repositories.ReviewRepo;
import com.codeup.betterreads.repositories.UserRepo;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
public class BookController {

    private BookRepo bookDao;
    private UserRepo userDao;
    private ReviewRepo reviewDao;
    private BookshelfRepo bookshelfDao;

    public BookController(BookRepo bookDao, UserRepo userDao, ReviewRepo reviewDao, BookshelfRepo bookshelfDao) {
        this.bookDao = bookDao;
        this.userDao = userDao;
        this.reviewDao = reviewDao;
        this.bookshelfDao = bookshelfDao;
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
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken) {
            viewModel.addAttribute("reviews", dbReviews);
            viewModel.addAttribute("book", newBook);
            return "books/viewbook";
        }
        else {
            User dbUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Bookshelf dbBookshelf = bookshelfDao.findBookshelfByUserIdAndBookId(dbUser.getId(), newBook.getId());
            Review dbReview = reviewDao.findByOwnerIdAndBookId(dbUser.getId(), newBook.getId());
            String isNull = "null";
            if(dbBookshelf == null) {
                viewModel.addAttribute("bookshelf", isNull);
            }
            else {
                viewModel.addAttribute("bookshelf", dbBookshelf);
            }
            if(dbReview != null) {
                viewModel.addAttribute("review", isNull);
                viewModel.addAttribute("editReview", dbReview);
            }
            else {
                viewModel.addAttribute("review", new Review());
                viewModel.addAttribute("editReview", isNull);
            }
            viewModel.addAttribute("user", dbUser);
            viewModel.addAttribute("reviews", dbReviews);
            viewModel.addAttribute("book", newBook);
            return "books/viewbook";
        }
    }

    @PostMapping("/book/{gbreference}")
    public String addToBookshelf(@PathVariable String gbreference,
                                 @RequestParam(value="bookshelfStatus") BookshelfStatus status
    ){
        User dbUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Book dbBook = bookDao.findBookByGbreferenceEquals(gbreference);
        Bookshelf currentBookshelf = bookshelfDao.findBookshelfByUserIdAndBookId(dbUser.getId(), dbBook.getId());

        Bookshelf dbBookshelf = new Bookshelf();
        dbBookshelf.setUser(dbUser);
        dbBookshelf.setBook(dbBook);
        dbBookshelf.setStatus(status);
        bookshelfDao.save(Objects.requireNonNullElse(currentBookshelf, dbBookshelf));

        return "redirect:/book/" + gbreference;
    }

    //will need to test these two PostMappings After Levi completes viewbook
    @PostMapping("/book/{gbreference}/createReview/")
    public String createReview(@PathVariable String gbreference,
                               @ModelAttribute Review dbReview
    ){
        User dbUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Book dbBook = bookDao.findBookByGbreferenceEquals(gbreference);
        dbReview.setOwner(dbUser);
        dbReview.setBook(dbBook);
        reviewDao.save(dbReview);
        return "redirect:/book/" + gbreference;
    }

    @PostMapping("/{username}/{gbreference}/editReview/{reviewId}")
    public String editReview(@PathVariable String username,
                             @PathVariable String gbreference,
                             @PathVariable long reviewId,
                             @ModelAttribute Review reviewToBeEdited
    ){
        User dbUser = userDao.findByUsername(username);
        Book dbBook = bookDao.findBookByGbreferenceEquals(gbreference);
        Review extractedReview = reviewDao.getOne(reviewId);

        reviewToBeEdited.setOwner(dbUser);
        reviewToBeEdited.setBook(dbBook);
        reviewToBeEdited.setId(reviewId);
        reviewToBeEdited.setCreatedDate(extractedReview.getCreatedDate());
        reviewDao.save(reviewToBeEdited);

        return "redirect:/book/" + gbreference;
    }

    @PostMapping("/book/{gbreference}/delete/id={reviewId}")
    public String deleteReview(@PathVariable String gbreference,
                               @PathVariable long reviewId
    ){
        reviewDao.deleteById(reviewId);
        return "redirect:/book/" + gbreference;
    }

}