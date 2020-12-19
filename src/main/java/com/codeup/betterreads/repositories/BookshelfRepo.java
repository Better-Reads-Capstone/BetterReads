package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.Book;
import com.codeup.betterreads.models.Bookshelf;
import com.codeup.betterreads.models.BookshelfStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookshelfRepo extends JpaRepository<Bookshelf, Long> {
    List<Bookshelf> findAllByUserId(long id);
    List<Bookshelf> findBookshelfByBook(Book id);
    List<Bookshelf> deleteBookshelfByBook(Book id);
}
