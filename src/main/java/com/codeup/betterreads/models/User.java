package com.codeup.betterreads.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 150)
    private String username;

    @Column(nullable = false, unique = true)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(length = 45)
    private String middleName;

    @Column(columnDefinition = "TEXT")
    private String aboutMe;

    @Column
    private String country;

    @Column
    private String websiteURL;

    @Column
    private String avatarURL;

    @Column
    private String pronouns;

    @Column
    @Temporal(TemporalType.DATE)
    private Date dob;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Club> clubs;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

    //default
    public User() {
    }

    //create
    public User(String email, String username, String password, String firstName, String middleName, String lastName, String aboutMe, String country, String websiteURL, String avatarURL, String pronouns, Date dob, Date createdDate) {
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
    }

    //read
    public User(long id, String email, String username, String password, String firstName, String middleName, String lastName, String aboutMe, String country, String websiteURL, String avatarURL, String pronouns, Date dob, Date createdDate) {
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

    //setters
    public void setId(long id) {this.id = id;}
    public void setEmail(String email) {this.email = email;}
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public void setMiddleName(String middleName) {this.middleName = middleName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setAboutMe(String aboutMe) {this.aboutMe = aboutMe;}
    public void setCountry(String country) {this.country = country;}
    public void setWebsiteURL(String websiteURL) {this.websiteURL = websiteURL;}
    public void setAvatarURL(String avatarURL) {this.avatarURL = avatarURL;}
    public void setDob(Date dob) {this.dob = dob;}
    public void setCreatedDate(Date createdDate) {this.createdDate = createdDate;}
}
