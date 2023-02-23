package com.brightslearning.webblog;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Entity
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long postID;
    private String message;
    private Date timestamp;
    @ManyToOne
    private BlogUser postOwner;

}