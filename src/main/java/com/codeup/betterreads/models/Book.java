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

    @OneToMany
    private List<ClubBook> clubBooks;

    public Book() {}

    //create
    public Book(String isbnTen, Genre genre){
        this.isbnTen = isbnTen;
        this.genre = genre;
    }

    //read
    public Book(long id, String isbnTen, Genre genre){
        this.id = id;
        this.isbnTen = isbnTen;
        this.genre = genre;
    }

    //getters
    public long getId() {return id;}
    public String getIsbnTen() {return isbnTen;}
    public Genre getGenre() {return genre;}

    //setters
    public void setId(long id) {this.id = id;}
    public void setIsbnTen(String isbnTen) {this.isbnTen = isbnTen;}
    public void setGenre(Genre genre) {this.genre = genre;}


}
