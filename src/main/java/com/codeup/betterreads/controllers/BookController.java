package com.codeup.betterreads.controllers;

import com.codeup.betterreads.models.Book;
import com.codeup.betterreads.repositories.BookRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {

    private BookRepo bookDao;

    public BookController(BookRepo bookDao) {
        this.bookDao = bookDao;
    }

    @GetMapping("/booksearch")
    public String searchResults() {return "books/results";}

    @GetMapping("/book/{gbreference}")
    public String createBook(@PathVariable String gbreference, Model viewModel) {
        Book newBook = bookDao.findBookByGbreferenceEquals(gbreference);
        Book newDbBook = new Book();

        if (newBook == null){
            newDbBook.setGbreference(gbreference);
            newBook = bookDao.save(newDbBook);
        }

        viewModel.addAttribute("book", newBook);
        return "books/viewbook";
    }

//    @GetMapping("/book/{isbn}")
//    public String showBook(
//            @PathVariable String isbn,
//            Model viewModel
//    )
//    {
//        Book dbBook = bookDao.findBookByIsbnEquals(isbn);
//        viewModel.addAttribute("book", dbBook);
//        return "books/viewbook";
//    }
}
