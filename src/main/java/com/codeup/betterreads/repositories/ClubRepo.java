package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepo extends JpaRepository<Club, Long> {
}
