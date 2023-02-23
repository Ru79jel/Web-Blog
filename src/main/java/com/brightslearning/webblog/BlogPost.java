package com.brightslearning.webblog;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long postID;
    private String message;
    private Date timestamp;

}