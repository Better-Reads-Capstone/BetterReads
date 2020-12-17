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
    public String searchResults() {return "book/results";}

    @PostMapping("/book/{isbn}")
    public String createBook(@PathVariable String isbn, Model viewModel) {
//        Book newBook = bookDao.findBookByIsbnTenEquals(isbn);
//        Book newDbBook = new Book();
//
//        System.out.println("newBook = " + newBook);
//
//        if (newBook == null){
//            newDbBook.setIsbnTen(isbn);
//            bookDao.save(newDbBook);
//        }
//
//        System.out.println("newDbBook = " + newDbBook);
//        viewModel.addAttribute("book", newDbBook);
        System.out.println("isbn = " + isbn);
        Book newBook = new Book();
        newBook.setIsbnTen(isbn);
        System.out.println("newBook.getIsbnTen() = " + newBook.getIsbnTen());
        Book dbBook = bookDao.save(newBook);
        System.out.println("dbBook.getId() = " + dbBook.getId());
        System.out.println("dbBook.getIsbnTen() = " + dbBook.getIsbnTen());

        viewModel.addAttribute("book", dbBook);

        return "book/viewbook";
    }

    @GetMapping("/book/{isbn}")
    public String showBook(
            @PathVariable String isbn,
            Model viewModel
    )
    {
        Book dbBook = bookDao.findBookByIsbnTenEquals(isbn);
        viewModel.addAttribute("book", dbBook);
        return "book/viewbook";
    }
//    {
//        System.out.println("isbn = " + isbn);
//        Book newBook = new Book();
//        newBook.setIsbnTen(isbn);
//        System.out.println("newBook.getIsbnTen() = " + newBook.getIsbnTen());
//        Book dbBook = bookDao.save(newBook);
//        System.out.println("dbBook.getIsbnTen() = " + dbBook.getIsbnTen());
//        System.out.println("dbBook.getId() = " + dbBook.getId());
//        viewModel.addAttribute("book", dbBook);
//        return "book/viewbook";
//    }
}
