package com.brightslearning.webblog.comment;

import com.brightslearning.webblog.post.BlogPost;
import com.brightslearning.webblog.user.BlogUser;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;



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
