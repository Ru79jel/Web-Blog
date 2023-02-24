package com.brightslearning.webblog;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class BlogUser {
    private String password;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userID;
    private String userName;
    @OneToMany(mappedBy = "postOwner")
    private Set<BlogPost> blogPosts;

    @OneToMany(mappedBy = "blogUser")
    private Set<BlogComment> comments;

    private boolean isAdmin;

    public BlogUser(String username, String password) {
        this.userName = username;
        this.password = password;
    }


}
