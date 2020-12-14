package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book, Long> {
}
