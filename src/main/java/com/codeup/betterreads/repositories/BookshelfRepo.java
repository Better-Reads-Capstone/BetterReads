package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.Book;
import com.codeup.betterreads.models.Bookshelf;
import com.codeup.betterreads.models.BookshelfStatus;
import com.codeup.betterreads.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookshelfRepo extends JpaRepository<Bookshelf, Long> {
    List<Bookshelf> findAllByUserId(long id);
    Bookshelf findBookshelfByBookAndUser(Book bookId, User userId);
    List<Bookshelf> deleteBookshelfByBookAndUser(Book bookId, User userId);
}
