package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    Review findByOwnerIdAndBookId(long ownerId, long bookId);
    List<Review> findAllByBookId(long bookId);
}
