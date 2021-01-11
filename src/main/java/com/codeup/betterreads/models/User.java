package com.codeup.betterreads.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "You must enter an email")
    @Column(nullable = false, unique = true)
    @JsonIgnore
    private String email;

    @NotBlank(message = "You must enter a username")
    @Column(nullable = false, unique = true, length = 150)
    @JsonIgnore
    private String username;

    @NotBlank(message = "You must enter a password")
    @Column(nullable = false, unique = true)
    @JsonIgnore
    private String password;

    @NotBlank(message = "You must enter a first name")
    @Column(nullable = false)
    @JsonIgnore
    private String firstName;

    @NotBlank(message = "You must enter a last name")
    @Column(nullable = false)
    @JsonIgnore
    private String lastName;

    @Column(length = 45)
    @JsonIgnore
    private String middleName;

    @Column(columnDefinition = "TEXT")
    @JsonIgnore
    private String aboutMe;

    @Column
    @JsonIgnore
    private String country;

    @Column
    @JsonIgnore
    private String websiteURL;

    @Column
    @JsonIgnore
    private String avatarURL;

    @Column
    @JsonIgnore
    private String pronouns;

    @Column
    @JsonIgnore
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dob;

    @Column(nullable = false)
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonBackReference
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonBackReference
    private List<ClubMember> clubMembers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    @JsonBackReference
    private List<Review> userReviews;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonBackReference
    private List<Bookshelf> bookshelves;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonBackReference
    private List<Comment> comments;

    //default
    public User() {
    }

    //create
    public User(String email, String username, String password, String firstName, String middleName, String lastName, String aboutMe, String country, String websiteURL, String avatarURL, String pronouns, Date dob, Date createdDate, List<Post> posts, List<ClubMember> clubMembers
    , List<Review> userReviews, List<Bookshelf> bookshelves, List<Comment> comments, String resetPasswordToken
    ) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.aboutMe = aboutMe;
        this.country = country;
        this.websiteURL = websiteURL;
        this.avatarURL = avatarURL;
        this.pronouns = pronouns;
        this.dob = dob;
        this.createdDate = createdDate;
        this.posts = posts;
        this.clubMembers = clubMembers;
        this.userReviews = userReviews;
        this.bookshelves = bookshelves;
        this.comments = comments;
        this.resetPasswordToken = resetPasswordToken;
    }

    //read
    public User(long id, String email, String username, String password, String firstName, String middleName, String lastName, String aboutMe, String country, String websiteURL, String avatarURL, String pronouns, Date dob, Date createdDate, List<Post> posts, List<ClubMember> clubMembers
            , List<Review> userReviews, List<Bookshelf> bookshelves, List<Comment> comments, String resetPasswordToken
    ) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.aboutMe = aboutMe;
        this.country = country;
        this.websiteURL = websiteURL;
        this.avatarURL = avatarURL;
        this.pronouns = pronouns;
        this.dob = dob;
        this.createdDate = createdDate;
        this.posts = posts;
        this.clubMembers = clubMembers;
        this.userReviews = userReviews;
        this.bookshelves = bookshelves;
        this.comments = comments;
        this.resetPasswordToken = resetPasswordToken;
    }

    //copy
    public User(User copy) {
        id = copy.id;
        email = copy.email;
        username = copy.username;
        password = copy.password;
        firstName = copy.firstName;
        middleName = copy.middleName;
        lastName = copy.lastName;
        aboutMe = copy.aboutMe;
        country = copy.country;
        websiteURL = copy.websiteURL;
        avatarURL = copy.avatarURL;
        pronouns = copy.pronouns;
        dob = copy.dob;
        createdDate = copy.createdDate;
        posts = copy.posts;
        clubMembers = copy.clubMembers;
        userReviews = copy.userReviews;
        bookshelves = copy.bookshelves;
        comments = copy.comments;
    }

    //getters
    public long getId() {return id;}
    public String getEmail() {return email;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public String getFirstName() {return firstName;}
    public String getMiddleName() {return middleName;}
    public String getLastName() {return lastName;}
    public String getAboutMe() {return aboutMe;}
    public String getCountry() {return country;}
    public String getWebsiteURL() {return websiteURL;}
    public String getAvatarURL() {return avatarURL;}
    public String getPronouns() {return pronouns;}
    public Date getDob() {return dob;}
    public Date getCreatedDate() {return createdDate;}
    public List<Post> getPosts() {return posts;}
    public List<ClubMember> getClubMembers() {return clubMembers;}
    public List<Review> getUserReviews() {return userReviews;}
    public List<Bookshelf> getBookshelves() {return bookshelves;}
    public List<Comment> getComments() {return comments;}
    public String getResetPasswordToken() {return resetPasswordToken;}

    //setters
    public void setId(long id) {this.id = id;}
    public void setEmail(String email) {this.email = email;}
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public void setMiddleName(String middleName) {this.middleName = middleName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setPronouns(String pronouns) {this.pronouns = pronouns;}
    public void setAboutMe(String aboutMe) {this.aboutMe = aboutMe;}
    public void setCountry(String country) {this.country = country;}
    public void setWebsiteURL(String websiteURL) {this.websiteURL = websiteURL;}
    public void setAvatarURL(String avatarURL) {this.avatarURL = avatarURL;}
    public void setDob(Date dob) {this.dob = dob;}
    public void setCreatedDate(Date createdDate) {this.createdDate = createdDate;}
    public void setPosts(List<Post> posts) {this.posts = posts;}
    public void setClubMembers(List<ClubMember> clubMembers) {this.clubMembers = clubMembers;}
    public void setUserReviews(List<Review> userReviews) {this.userReviews = userReviews;}
    public void setBookshelves(List<Bookshelf> bookshelves) {this.bookshelves = bookshelves;}
    public void setComments(List<Comment> comments) {this.comments = comments;}
    public void setResetPasswordToken(String resetPasswordToken) {this.resetPasswordToken = resetPasswordToken;}
}
