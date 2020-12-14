package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubMemberRepo extends JpaRepository<ClubMember, Long> {
}
