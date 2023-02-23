package com.brightslearning.webblog;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class WebService {
    private UserRepo userRepo;
    private PostRepo postRepo;
    private CommentRepo commentRepo;

    @Autowired
    public WebService(UserRepo userRepo, PostRepo postRepo, CommentRepo commentRepo) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
    }
}
