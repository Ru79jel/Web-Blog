package com.brightslearning.webblog.post;

import com.brightslearning.webblog.user.BlogUser;
import com.brightslearning.webblog.comment.BlogComment;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long postID;
    private String message;
    private LocalDate timestamp;
    private String title;
    private LocalDate lastEditAt;
    @ManyToOne
    private BlogUser postOwner;
    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.REMOVE)
    private Set<BlogComment> comments;

    public BlogPost() {
        this.timestamp = LocalDate.now();
    }
}