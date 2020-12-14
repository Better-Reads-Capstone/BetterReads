package com.codeup.betterreads.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @ManyToOne
    @JoinColumn (name = "club_id")
    private Club club;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    //CONSTRUCTORS
    // default
    public Post() {}

    //create
    public Post(String title, String body, Date createdDate, Date updatedDate, Club club, User user) {
        this.title = title;
        this.body = body;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.club = club;
        this.user = user;
    }

    //read
    public Post(long id, String title, String body, Date createdDate, Date updatedDate, Club club, User user) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.club = club;
        this.user = user;
    }

    //GETTERS
    public long getId() {return id;}
    public String getTitle() {return title;}
    public String getBody() {return body;}
    public Date getCreatedDate() {return createdDate;}
    public Date getUpdatedDate() {return updatedDate;}
    public Club getClub() {return club;}
    public User getUser() {return user;}

    //SETTERS
    public void setId(long id) {this.id = id;}
    public void setTitle(String title) {this.title = title;}
    public void setBody(String body) {this.body = body;}
    public void setCreatedDate(Date createdDate) {this.createdDate = createdDate;}
    public void setUpdatedDate(Date updatedDate) {this.updatedDate = updatedDate;}
    public void setUser(User user) {this.user = user;}
    public void setClub(Club club) {this.club = club;}
}
