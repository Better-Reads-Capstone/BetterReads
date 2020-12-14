package com.codeup.betterreads.models;

import javax.persistence.*;

@Entity
@Table(name = "club_members")
public class ClubMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private boolean isAdmin;

    //constructors
    //default
    public ClubMember(){}

    public ClubMember(Club club, User user, boolean isAdmin){
        this.club = club;
        this.user = user;
        this.isAdmin = isAdmin;
    }

    public ClubMember(long id, Club club, User user, boolean isAdmin){
        this.id = id;
        this.club = club;
        this.user = user;
        this.isAdmin = isAdmin;
    }

    //getters
    public long getId(){return id;}
    public Club getClub(){return club;}
    public User getUser(){return user;}
    public boolean getIsAdmin(){return isAdmin;}

    //setters
    public void setId(long id){this.id = id;}
    public void setClub(Club club){this.club = club;}
    public void setUser(User user){this.user = user;}
    public void setIsAdmin(boolean isAdmin){this.isAdmin = isAdmin;}

}
