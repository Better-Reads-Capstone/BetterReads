package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    Review findByOwnerIdAndBookId(long ownerId, long bookId);
}
