package com.brightslearning.webblog;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private LocalDate timestamp;
    private String title;

    private LocalDate lastEditAt;

    @ManyToOne
    private BlogUser postOwner;

    @OneToMany(mappedBy = "blogPost")
    private Set<BlogComment> comments;

    public BlogPost() {
        this.timestamp = LocalDate.now();
    }
}