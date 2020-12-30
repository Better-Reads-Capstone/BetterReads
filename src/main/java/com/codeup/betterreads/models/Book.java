package com.codeup.betterreads.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 25)
    private String gbreference;

    @Column(nullable = true, length = 15)
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    private List<ClubBook> clubBooks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    @JsonBackReference
    private List<Review> bookReviews;


    public Book() {}

    //create
    public Book(String gbreference, String isbn, Genre genre, List<ClubBook> clubBooks
    , List<Review> bookReviews
    ){
        this.gbreference = gbreference;
        this.isbn = isbn;
        this.genre = genre;
        this.clubBooks = clubBooks;
        this.bookReviews = bookReviews;
    }

    //read
    public Book(long id, String gbreference, String isbn, Genre genre, List<ClubBook> clubBooks
    , List<Review> bookReviews
    ){
        this.id = id;
        this.gbreference = gbreference;
        this.isbn = isbn;
        this.genre = genre;
        this.clubBooks = clubBooks;
        this.bookReviews = bookReviews;
    }

    //getters
    public long getId() {return id;}
    public String getGbreference() {return gbreference;}
    public String getIsbn() {return isbn;}
    public Genre getGenre() {return genre;}
    public List<ClubBook> getClubBooks() {return clubBooks;}
    public List<Review> getBookReviews() {return bookReviews;}

    //setters
    public void setId(long id) {this.id = id;}
    public void setGbreference(String gbreference) {this.gbreference = gbreference;}
    public void setIsbn(String isbnTen) {this.isbn = isbnTen;}
    public void setGenre(Genre genre) {this.genre = genre;}
    public void setClubBooks(List<ClubBook> clubBooks) {this.clubBooks = clubBooks;}
    public void setBookReviews(List<Review> bookReviews) {this.bookReviews = bookReviews;}
}
