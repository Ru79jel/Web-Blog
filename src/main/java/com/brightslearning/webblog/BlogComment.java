package com.brightslearning.webblog;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

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

    private Date timestamp;

}
