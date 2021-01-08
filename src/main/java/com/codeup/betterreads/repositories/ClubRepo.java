package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.Club;
import com.codeup.betterreads.models.Genre;
import com.codeup.betterreads.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepo extends JpaRepository<Club, Long> {
    List<Club> findByNameIsLike(String query);
    List<Club> findByGenreIdAndNameIsLike(long id, String query);
    List<Club> findAllByOwnerId(long id);
    List<Club> findAllByOwner(User user);
}
