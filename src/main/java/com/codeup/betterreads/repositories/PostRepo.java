package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.Club;
import com.codeup.betterreads.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {

    List<Post> findAllByClub(Club club);
}
