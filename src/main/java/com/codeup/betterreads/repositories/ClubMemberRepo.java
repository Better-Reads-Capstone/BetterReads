package com.codeup.betterreads.repositories;

import com.codeup.betterreads.models.Club;
import com.codeup.betterreads.models.ClubMember;
import com.codeup.betterreads.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubMemberRepo extends JpaRepository<ClubMember, Long> {
    ClubMember findClubMemberByUserAndClub(User user, Club club);
    List<ClubMember> findAllByClub(Club club);
}
