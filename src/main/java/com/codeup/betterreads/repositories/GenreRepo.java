package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepo extends JpaRepository<Genre, Long> {
    List<Genre> findByNameIsLike(String name);
}
