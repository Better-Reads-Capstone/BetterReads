package com.codeup.betterreads.models;

import javax.persistence.*;
import java.util.Date;

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

}
