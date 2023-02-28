package com.brightslearning.webblog.user;

import com.brightslearning.webblog.comment.BlogComment;
import com.brightslearning.webblog.post.BlogPost;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class BlogUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userID;
    private String userName;


    private String password;
    private boolean isAdmin;
    @OneToMany(mappedBy = "postOwner")
    private Set<BlogPost> blogPosts;
    @OneToMany(mappedBy = "blogUser")
    private Set<BlogComment> comments;

    public BlogUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
