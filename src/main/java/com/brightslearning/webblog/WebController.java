package com.brightslearning.webblog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

    private WebService webService;

    @Autowired
    public WebController(WebService webService) {
        this.webService = webService;
    }

    @GetMapping("/")
    public String showPosts(Model model) {
        model.addAttribute("posts", this.webService.getPostRepo().findAll(Sort.by(Sort.Direction.DESC, "timestamp")));
        model.addAttribute("newpost", new Post());
        return "index";
    }

    @PostMapping("/")
    public String newPost(@ModelAttribute Post post, Model model) {
        this.webService.getPostRepo().save(post);
        model.addAttribute("posts", this.webService.getPostRepo().findAll());
        model.addAttribute("newpost", new Post());
        return "index";
    }

    @GetMapping("/{id}/comments")
    public String showComments(Model model) {
        model.addAttribute("posts", this.webService.getCommentRepo().findAll());
        model.addAttribute("newpost", new Comment());
        return "viewcomments";
    }

    @PostMapping("/{id}/comments")
    public String newComment(@ModelAttribute Comment comment, Model model) {
        this.webService.getCommentRepo().save(comment);
        model.addAttribute("comments", this.webService.getCommentRepo().findAll());
        model.addAttribute("newcomment", new Comment());
        return "index";
    }

}
