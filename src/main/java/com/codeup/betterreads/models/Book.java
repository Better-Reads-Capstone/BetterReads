package com.codeup.betterreads.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 11)
    private String isbnTen;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    private List<ClubBook> clubBooks;


    public Book() {}

    //create
    public Book(String isbnTen, Genre genre, List<ClubBook> clubBooks){
        this.isbnTen = isbnTen;
        this.genre = genre;
        this.clubBooks = clubBooks;
    }

    //read
    public Book(long id, String isbnTen, Genre genre, List<ClubBook> clubBooks){
        this.id = id;
        this.isbnTen = isbnTen;
        this.genre = genre;
        this.clubBooks = clubBooks;
    }

    //getters
    public long getId() {return id;}
    public String getIsbnTen() {return isbnTen;}
    public Genre getGenre() {return genre;}
    public List<ClubBook> getClubBooks() {return clubBooks;}

    //setters
    public void setId(long id) {this.id = id;}
    public void setIsbnTen(String isbnTen) {this.isbnTen = isbnTen;}
    public void setGenre(Genre genre) {this.genre = genre;}
    public void setClubBooks(List<ClubBook> clubBooks) {this.clubBooks = clubBooks;}
}
