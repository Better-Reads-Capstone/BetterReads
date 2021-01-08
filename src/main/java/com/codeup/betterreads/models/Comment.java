package com.codeup.betterreads.models;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String body;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn (name = "post_id")
    private Post post;

    //Constructors
//default
    public Comment() {}

    public Comment(String body, Date createdDate, Date updatedDate, Post post, User user) {
        this.body = body;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.post = post;
        this.user = user;
    }

    public Comment(long id, String body, Date createdDate, Date updatedDate, Post post, User user) {
        this.id = id;
        this.body = body;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.post = post;
        this.user = user;
    }

    //Getters
    public long getId() {return id;}
    public String getBody() {return body;}
    public Date getCreatedDate() {return createdDate;}
    public Date getUpdatedDate() {return updatedDate;}
    public Post getPost() {return post;}
    public User getUser() {return user;}

    //Setters
    public void setId(long id) {this.id = id;}
    public void setBody(String body) {this.body = body;}
    public void setCreatedDate(Date createdDate) {this.createdDate = createdDate;}
    public void setUpdatedDate(Date updatedDate) {this.updatedDate = updatedDate;}
    public void setUser(User user) {this.user = user;}
    public void setPost(Post post) {this.post = post;}

}



