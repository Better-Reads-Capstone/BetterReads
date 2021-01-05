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
            System.out.println("nada");
        }
        else {
            User dbUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Bookshelf dbBookshelf = bookshelfDao.findBookshelfByUserIdAndBookId(dbUser.getId(), newBook.getId());
            System.out.println("test getting bookshelf: " + dbBookshelf);
            String isNull = "null";
            if(dbBookshelf == null) {
                viewModel.addAttribute("bookshelf", isNull);
            }
            else {
                viewModel.addAttribute("bookshelf", dbBookshelf);
            }
            viewModel.addAttribute("user", dbUser);
            viewModel.addAttribute("review", new Review());
        }
        

        viewModel.addAttribute("reviews", dbReviews);
        viewModel.addAttribute("book", newBook);
        return "books/viewbook";
    }

    @PostMapping("/book/{gbreference}")
    public String addToBookshelf(@PathVariable String gbreference,
                                 @RequestParam(value="bookshelfStatus") BookshelfStatus status
    ){
        User dbUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Book dbBook = bookDao.findBookByGbreferenceEquals(gbreference);
        Bookshelf currentBookshelf = bookshelfDao.findBookshelfByUserIdAndBookId(dbUser.getId(), dbBook.getId());
        System.out.println(currentBookshelf);

        Bookshelf dbBookshelf = new Bookshelf();
        dbBookshelf.setUser(dbUser);
        dbBookshelf.setBook(dbBook);
        dbBookshelf.setStatus(status);
        bookshelfDao.save(Objects.requireNonNullElse(currentBookshelf, dbBookshelf));


        return "redirect:/book/" + gbreference;
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