package com.codeup.betterreads.models;

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


    public Book() {}

    //create
    public Book(String gbreference, String isbn, Genre genre, List<ClubBook> clubBooks){
        this.gbreference = gbreference;
        this.isbn = isbn;
        this.genre = genre;
        this.clubBooks = clubBooks;
    }

    //read
    public Book(long id, String gbreference, String isbn, Genre genre, List<ClubBook> clubBooks){
        this.id = id;
        this.gbreference = gbreference;
        this.isbn = isbn;
        this.genre = genre;
        this.clubBooks = clubBooks;
    }

    //getters
    public long getId() {return id;}
    public String getGbreference() {return gbreference;}
    public String getIsbn() {return isbn;}
    public Genre getGenre() {return genre;}
    public List<ClubBook> getClubBooks() {return clubBooks;}

    //setters
    public void setId(long id) {this.id = id;}
    public void setGbreference(String gbreference) {this.gbreference = gbreference;}
    public void setIsbn(String isbnTen) {this.isbn = isbnTen;}
    public void setGenre(Genre genre) {this.genre = genre;}
    public void setClubBooks(List<ClubBook> clubBooks) {this.clubBooks = clubBooks;}
}
