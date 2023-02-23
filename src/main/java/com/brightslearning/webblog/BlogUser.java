package com.brightslearning.webblog;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class BlogUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userID;
    private String userName;

    @OneToMany(mappedBy = "blogUser")
    private Set<BlogComment> comments;

    private boolean isAdmin;


}
