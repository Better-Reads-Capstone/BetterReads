package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.ClubBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubBookRepo extends JpaRepository<ClubBook, Long> {
}
