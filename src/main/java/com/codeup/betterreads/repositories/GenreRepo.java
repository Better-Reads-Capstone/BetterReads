package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepo extends JpaRepository<Genre, Long> {
}
