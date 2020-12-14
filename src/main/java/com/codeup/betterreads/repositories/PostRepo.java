package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Long> {
}
