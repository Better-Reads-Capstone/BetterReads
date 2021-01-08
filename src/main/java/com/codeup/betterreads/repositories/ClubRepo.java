package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepo extends JpaRepository<Club, Long> {
    List<Club> findByNameOrGenreIsLike(String query, String query2);
}
