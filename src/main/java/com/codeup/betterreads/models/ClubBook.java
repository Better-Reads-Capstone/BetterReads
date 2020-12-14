package com.codeup.betterreads.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "club_books")
public class ClubBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    //constructors?
    public ClubBook(){}

    public ClubBook(Club club, Book book, Date createdDate){
        this.club = club;
        this.book = book;
        this.createdDate = createdDate;
    }

    public ClubBook(long id, Club club, Book book, Date createdDate){
        this.id = id;
        this.club = club;
        this.book = book;
        this.createdDate = createdDate;
    }

    //getters?
    public long getId(){return id;}
    public Club getClub(){return club;}
    public Book getBook(){return book;}
    public Date getCreatedDate(){return createdDate;}

    //setters?
    public void setId(long id){this.id = id;}
    public void setClub(Club club){this.club = club;}
    public void setBook(Book book){this.book = book;}
    public void setCreatedDate(Date createdDate){this.createdDate = createdDate;}

    //todo members will be the same, add id

}
