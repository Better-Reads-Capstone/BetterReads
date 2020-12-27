package com.codeup.betterreads.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "polls")
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "book1_id")
    private Book bookOne;

    @ManyToOne
    @JoinColumn(name = "book2_id")
    private Book bookTwo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    @CreationTimestamp
    private Date createdDate;

    public Poll(){}

    public Poll(boolean active, Club club, Book bookOne, Book bookTwo, User user, Date createdDate){
        this.active = active;
        this.club = club;
        this.bookOne = bookOne;
        this.bookTwo = bookTwo;
        this.user = user;
        this.createdDate = createdDate;
    }

    public Poll(long id, boolean active, Club club, Book bookOne, Book bookTwo, User user, Date createdDate){
        this.id = id;
        this.active = active;
        this.club = club;
        this.bookOne = bookOne;
        this.bookTwo = bookTwo;
        this.user = user;
        this.createdDate = createdDate;
    }

    public long getId(){return id;}
    public boolean getActive(){return active;}
    public Club getClub(){return club;}
    public Book getBookOne(){return bookOne;}
    public Book getBookTwo(){return bookTwo;}
    public User getUser(){return user;}
    public Date getCreatedDate(){return createdDate;}

    public void setId(long id){this.id = id;}
    public void setActive(boolean active){this.active = active;}
    public void setClub(Club club){this.club = club;}
    public void setBookOne(Book bookOne){this.bookOne = bookOne;}
    public void setBookTwo(Book bookTwo){this.bookTwo = bookTwo;}
    public void setUser(User user){this.user = user;}
    public void setCreatedDate(Date createdDate){this.createdDate = createdDate;}
}
