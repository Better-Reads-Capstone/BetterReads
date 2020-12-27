package com.codeup.betterreads.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @CreationTimestamp
    private Date createdDate;

    @Column
    @UpdateTimestamp
    private Date updatedDate;

    @ManyToOne
    @JoinColumn (name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn (name = "book_id")
    private Book book;

    //default
    public Review() {}

    //create
    public Review(int rating, String body, Date createdDate, Date updatedDate, User owner,
//                  Book bookId
                  Book book
    ) {
        this.rating = rating;
        this.body = body;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.owner = owner;
//        this.bookId = bookId;
        this.book = book;
    }

    //read
    public Review(long id, int rating, String body, Date createdDate, Date updatedDate, User owner,
//                  Book bookId
                  Book book
    ) {
        this.id = id;
        this.rating = rating;
        this.body = body;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.owner = owner;
//        this.bookId = bookId;
        this.book = book;
    }

    public long getId() {return id;}
    public int getRating() {return rating;}
    public String getBody() {return body;}
    public Date getCreatedDate() {return createdDate;}
    public Date getUpdatedDate() {return updatedDate;}
    public User getOwner() {return owner;}
//    public Book getBookId() {return bookId;}
    public Book getBook() {return book;}

    public void setId(long id) {this.id = id;}
    public void setRating(int rating) {this.rating = rating;}
    public void setBody(String body) {this.body = body;}
    public void setCreatedDate(Date createdDate) {this.createdDate = createdDate;}
    public void setUpdatedDate(Date updatedDate) {this.updatedDate = updatedDate;}
    public void setOwner(User owner) {this.owner = owner;}
//    public void setBookId(Book bookId) {this.bookId = bookId;}
    public void setBook(Book book) {this.book = book;}
}
