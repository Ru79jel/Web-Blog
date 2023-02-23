package com.brightslearning.webblog;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class BlogUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userID;
    private String userName;
    @OneToMany(mappedBy = "postOwner")
    private List<BlogPost> blogPosts;
    private boolean isAdmin;


}
