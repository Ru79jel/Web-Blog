package com.brightslearning.webblog;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class BlogUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userID;
    private String userName;
    private String password;
    @OneToMany(mappedBy = "postOwner")
    private Set<BlogPost> blogPosts;

    @OneToMany(mappedBy = "blogUser")
    private Set<BlogComment> comments;


    private boolean isAdmin;


    public BlogUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
