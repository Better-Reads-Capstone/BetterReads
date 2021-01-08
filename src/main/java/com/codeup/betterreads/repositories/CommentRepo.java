package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.Comment;
import com.codeup.betterreads.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
}
