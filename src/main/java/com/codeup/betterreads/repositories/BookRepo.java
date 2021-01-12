package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book, Long> {
    Book findBookById(long id);
    Book findBookByIsbnEquals(String isbn);
    Book findBookByGbreferenceEquals(String gbreference);
}
