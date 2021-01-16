package com.codeup.betterreads.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "clubs")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NotBlank(message = "A club must have a name.")
    private String name;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "A club requires a description.")
    @Size(min = 25, message = "Minimum description size is 25 characters.")
    private String about;

    @Column
    private String headerImageUrl;

    @Column
    private boolean privacy;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn (name = "owner_id")
    private User owner;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn (name = "genre_id")
    private Genre genre;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "club")
    @JsonBackReference
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "club")
    @JsonBackReference
    private List<ClubBook> clubBooks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "club")
    @JsonBackReference
    private List<ClubMember> clubMembers;

    //default
    public Club() {}

    //create
    public Club(String name, Date createdDate, String about, String headerImageUrl, boolean privacy, User owner, Genre genre, List<Post> posts, List<ClubBook> clubBooks, List<ClubMember> clubMembers) {
        this.name = name;
        this.createdDate = createdDate;
        this.about = about;
        this.headerImageUrl = headerImageUrl;
        this.privacy = privacy;
        this.owner = owner;
        this.genre = genre;
        this.posts = posts;
        this.clubBooks = clubBooks;
        this.clubMembers = clubMembers;
    }

    //read
    public Club(long id, String name, Date createdDate, String about, String headerImageUrl, boolean privacy, User owner, Genre genre, List<Post> posts, List<ClubBook> clubBooks, List<ClubMember> clubMembers) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.about = about;
        this.headerImageUrl = headerImageUrl;
        this.privacy = privacy;
        this.owner = owner;
        this.genre = genre;
        this.posts = posts;
        this.clubBooks = clubBooks;
        this.clubMembers = clubMembers;
    }

    //getters
    public long getId() { return id; }
    public String getName() { return name; }
    public Date getCreatedDate() { return createdDate; }
    public String getAbout() { return about; }
    public String getHeaderImageUrl() { return headerImageUrl; }
    public boolean getPrivacy() { return privacy; }
    public User getOwner() { return owner; }
    public Genre getGenre() { return genre; }
    public List<Post> getPosts() { return posts; }
    public List<ClubBook> getClubBooks() { return clubBooks; }
    public List<ClubMember> getClubMembers() { return clubMembers; }

    //setters
    public void setId(long id) { this.id = id;}
    public void setName(String name) { this.name = name;}
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate;}
    public void setAbout(String about) { this.about = about;}
    public void setHeaderImageUrl(String headerImageUrl) { this.headerImageUrl = headerImageUrl;}
    public void setPrivacy(boolean privacy) { this.privacy = privacy;}
    public void setOwner(User owner) { this.owner = owner;}
    public void setGenre(Genre genre) { this.genre = genre;}
    public void setPosts(List<Post> posts) { this.posts = posts;}
    public void setClubBooks(List<ClubBook> clubBooks) { this.clubBooks = clubBooks;}
    public void setClubMembers(List<ClubMember> clubMembers) { this.clubMembers = clubMembers;}
}
