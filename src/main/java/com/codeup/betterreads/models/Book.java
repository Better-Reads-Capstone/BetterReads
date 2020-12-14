package com.codeup.betterreads.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int isbn;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany
    private List<ClubBook> clubBooks;

    public Book() {}

    //create
    public Book(int isbn, Genre genre){
        this.isbn = isbn;
        this.genre = genre;
    }

    //read
    public Book(long id, int isbn, Genre genre){
        this.id = id;
        this.isbn = isbn;
        this.genre = genre;
    }

    //getters
    public long getId() {return id;}
    public int getIsbn() {return isbn;}
    public Genre getGenre() {return genre;}

    //setters
    public void setId(long id) {this.id = id;}
    public void setIsbn(int isbn) {this.isbn = isbn;}
    public void setGenre(Genre genre) {this.genre = genre;}


}
