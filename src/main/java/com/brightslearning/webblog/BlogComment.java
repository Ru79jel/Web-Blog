package com.brightslearning.webblog;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
public class BlogComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
