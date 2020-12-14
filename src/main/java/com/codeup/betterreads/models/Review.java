package com.codeup.betterreads.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int rating;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @ManyToOne
    @JoinColumn (name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn (name = "book_id")
    private Book bookId;

    //default
    public Review() {}

    //create
    public Review(int rating, String body, Date createdDate, Date updatedDate, User owner, Book bookId) {
        this.rating = rating;
        this.body = body;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.owner = owner;
        this.bookId = bookId;
    }

    //read
    public Review(long id, int rating, String body, Date createdDate, Date updatedDate, User owner, Book bookId) {
        this.id = id;
        this.rating = rating;
        this.body = body;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.owner = owner;
        this.bookId = bookId;
    }

    public long getId() {return id;}
    public int getRating() {return rating;}
    public String getBody() {return body;}
    public Date getCreatedDate() {return createdDate;}
    public Date getUpdatedDate() {return updatedDate;}
    public User getOwner() {return owner;}
    public Book getBookId() {return bookId;}

    public void setId(long id) {this.id = id;}
    public void setRating(int rating) {this.rating = rating;}
    public void setBody(String body) {this.body = body;}
    public void setCreatedDate(Date createdDate) {this.createdDate = createdDate;}
    public void setUpdatedDate(Date updatedDate) {this.updatedDate = updatedDate;}
    public void setOwner(User owner) {this.owner = owner;}
    public void setBookId(Book bookId) {this.bookId = bookId;}
}