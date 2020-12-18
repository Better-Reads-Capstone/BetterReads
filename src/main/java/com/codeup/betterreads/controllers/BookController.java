package com.codeup.betterreads.controllers;

import com.codeup.betterreads.models.Book;
import com.codeup.betterreads.repositories.BookRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    private BookRepo bookDao;

    @Value("${googleBooksAPIKey}")
    private String bgapikey;

    @Value("${nytAPIKey}")
    private String nytapikey;

    @Value("${filestackAPIKey}")
    private String filestackkey;

    public BookController(BookRepo bookDao) {
        this.bookDao = bookDao;
    }

    @GetMapping("/booksearch")
    public String searchResults() {return "books/results";}

    @PostMapping("/book/{isbn}")
    public String createBook(@PathVariable String isbn, Model viewModel) {
        Book newBook = bookDao.findBookByIsbnTenEquals(isbn);
        Book newDbBook = new Book();

        if (newBook == null){
            newDbBook.setIsbnTen(isbn);
            newBook = bookDao.save(newDbBook);
        }

        viewModel.addAttribute("book", newBook);
        return "books/viewbook";
    }

    @GetMapping("/book/{isbn}")
    public String showBook(
            @PathVariable String isbn,
            Model viewModel
    )
    {
        Book dbBook = bookDao.findBookByIsbnTenEquals(isbn);
        viewModel.addAttribute("book", dbBook);
        return "books/viewbook";
    }

    // Creates a psuedo keys.js file
    // TODO: Need to lockdown access to this path to internal requests only (CORS/CSRF/Security settings)
    @RequestMapping(path = "/keys.js", produces = "application/javascript")
    @ResponseBody
    public String apikeys(){
        String filedata = String.format("const gbKey='%s';%nconst nytKey='%s';%nconst fsKey='%s'", bgapikey, nytapikey, filestackkey);
        return filedata;
    }

    @GetMapping("/books")
    public String showBooks(){
        return "books/playground";
    }
}
