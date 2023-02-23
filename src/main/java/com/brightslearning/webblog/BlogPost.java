package com.brightslearning.webblog;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long postID;
    private String message;
    private Date timestamp;
    private String title;
    @ManyToOne
    private BlogUser postOwner;

    @OneToMany(mappedBy = "blogPost")
    private Set<BlogComment> comments;

}