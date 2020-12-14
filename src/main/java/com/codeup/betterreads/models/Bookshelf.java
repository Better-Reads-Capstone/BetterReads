package com.codeup.betterreads.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_bookshelves")

public class Bookshelf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookshelfStatus status;

    public Bookshelf() {}

    //create
    public Bookshelf(User user, Book book){
        this.user = user;
        this.book = book;
    }

    //read
    public Bookshelf(long id, User user, Book book){
        this.id = id;
        this.user = user;
        this.book = book;
    }

    //getters
    public long getId(){return id;}
    public User getUser(){return user;}
    public Book getBook(){return book;}

    //setters
    public void setId(long id){this.id = id;}
    public void setUser(User user){this.user = user;}
    public void setBook(Book book){this.book = book;}

}
