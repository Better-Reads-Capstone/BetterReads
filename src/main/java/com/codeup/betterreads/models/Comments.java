//package com.codeup.betterreads.models;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "comments")
//public class Comments {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @Column(nullable = false, columnDefinition = "TEXT")
//    private String body;
//
//    @ManyToOne
//    @JoinColumn (name = "user_id")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn (name = "post_id")
//    private Post post;
//}
