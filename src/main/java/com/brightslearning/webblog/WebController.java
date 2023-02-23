package com.brightslearning.webblog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {

    private WebService webService;

    @Autowired
    public WebController(WebService webService) {
        this.webService = webService;
        for (int i = 1; i < 25; i++) {
            BlogUser user = new BlogUser();
            user.setUserName("User#" + i);
            this.webService.getUserRepo().save(user);
        }
        for (int i = 1; i < 25; i++) {
            BlogPost post = new BlogPost();
            post.setMessage(i + ". Message");
            post.setTitle("Message #" + i);
            post.setPostOwner(this.webService.getUserRepo().findById(Long.valueOf((int) (Math.random() * (1 - 25) + 25))).get());
            this.webService.getPostRepo().save(post);

        }

    }

    @GetMapping("/")
    public String showBlogBlogPosts(Model model) {
        model.addAttribute("posts", this.webService.getPostRepo().findAll());
        model.addAttribute("newpost", new BlogPost());
        return "index";
    }

    @PostMapping("/")
    public String newBlogPost(@ModelAttribute BlogPost post, Model model) {
        this.webService.getPostRepo().save(post);
        model.addAttribute("posts", this.webService.getPostRepo().findAll());
        model.addAttribute("newpost", new BlogPost());
        return "index";
    }

    @GetMapping("/{id}/comments")
    public String showComments(@PathVariable long id, Model model) {
        model.addAttribute("comments", this.webService.getCommentRepo().findAll());
        model.addAttribute("newcomment", new BlogComment());
        model.addAttribute("post", this.webService.getPostRepo().findById(id));
        return "viewcomments";
    }

    @PostMapping("/{id}/comments")
    public String newComment(@ModelAttribute BlogComment comment, @PathVariable long id, Model model) {
        this.webService.getCommentRepo().save(comment);
        model.addAttribute("post", this.webService.getPostRepo().findById(id));
        model.addAttribute("comments", this.webService.getCommentRepo().findAll());
        model.addAttribute("newcomment", new BlogComment());

        return "index";
    }

}
