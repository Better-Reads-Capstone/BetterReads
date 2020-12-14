package com.codeup.betterreads.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "genre")
    private List<Club> clubs;

    //CONSTRUCTORS
    //default
    public Genre() {}

    //create
    public Genre(String name) {
        this.name = name;
    }

    //read
    public Genre(long id, String name) {
        this.id = id;
        this.name = name;
    }

    //GETTERS
    public long getId() { return id;}
    public String getName() { return name;}

    //SETTERS
    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
}
