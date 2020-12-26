package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.Book;
import com.codeup.betterreads.models.Review;
import com.codeup.betterreads.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    Review findByOwnerIdAndBookId(long ownerId, long bookId);
}
