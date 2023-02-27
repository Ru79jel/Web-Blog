package com.brightslearning.webblog;

import com.brightslearning.webblog.post.BlogPost;
import com.brightslearning.webblog.post.PostRepo;
import com.brightslearning.webblog.user.BlogUser;
import com.brightslearning.webblog.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestDataController {
    private UserRepo userRepo;
    private PostRepo postRepo;


    @Autowired
    public TestDataController(UserRepo userRepo, PostRepo postRepo) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
    }

    @GetMapping("/createTestdata")
    public @ResponseBody String createTestDate() {
        for (int i = 1; i < 25; i++) {
            BlogUser user = new BlogUser();
            user.setUserName("User#" + i);
            this.userRepo.save(user);
        }
        for (int i = 1; i < 25; i++) {
            BlogPost post = new BlogPost();
            post.setMessage(i + ". Message");
            post.setTitle("Message #" + i);
            post.setPostOwner(this.userRepo.findById(Long.valueOf((int) (Math.random() * (1 - 25) + 25))).get());
            post.setPostOwner(this.userRepo.findById((long) i).get());
            this.postRepo.save(post);

        }
        return "Testdata created";
    }
}
