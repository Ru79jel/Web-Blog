package com.brightslearning.webblog;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userID;
    private String userName;
    private BlogPost blogPost;
    private boolean isAdmin;


}
