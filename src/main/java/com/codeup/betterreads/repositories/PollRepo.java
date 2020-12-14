package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepo extends JpaRepository<Poll, Long> {
}
