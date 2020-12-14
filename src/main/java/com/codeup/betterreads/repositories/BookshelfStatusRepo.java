package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.BookshelfStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookshelfStatusRepo extends JpaRepository<BookshelfStatus, Long> {
}
