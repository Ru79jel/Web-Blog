package com.brightslearning.webblog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
        BlogPost blogPost = this.webService.getPostRepo().findById(id).get();
        model.addAttribute("post", blogPost);
        return "viewcomments";
    }

    @PostMapping("/{id}/comments")
    public String newComment(@ModelAttribute BlogComment comment, @PathVariable long id, Model model) {
        this.webService.getCommentRepo().save(comment);
        BlogPost blogPost = this.webService.getPostRepo().findById(id).get();
        model.addAttribute("post", blogPost);
        model.addAttribute("comments", this.webService.getCommentRepo().findAll());
        model.addAttribute("newcomment", new BlogComment());
        return "viewcomments";
    }

}
