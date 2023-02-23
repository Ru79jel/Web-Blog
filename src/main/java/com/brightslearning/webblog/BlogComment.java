package com.brightslearning.webblog;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

@Data
@Entity
public class BlogComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private BlogUser blogUser;
    @ManyToOne
    private BlogPost blogPost;

    private String content;

    private LocalDate timestamp;

    public BlogComment() {
        this.timestamp = LocalDate.now();
    }
}
