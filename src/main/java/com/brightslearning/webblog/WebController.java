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
        model.addAttribute("posts", this.webService.getPostRepo().findByOrderByTimestampDesc());
        model.addAttribute("newpost", new BlogPost());
        return "index";
    }

    @PostMapping("/")
    public String newBlogPost(@ModelAttribute BlogPost post, Model model) {
        this.webService.getPostRepo().save(post);
        model.addAttribute("posts", this.webService.getPostRepo().findByOrderByTimestampDesc());
        model.addAttribute("newpost", new BlogPost());
        return "index";
    }

    @GetMapping("/{postId}/comments")
    public String showComments(@PathVariable long postId, Model model) {
        model.addAttribute("comments", this.webService.getCommentRepo().findByBlogPostPostIDOrderByTimestampAsc(postId));
        model.addAttribute("newcomment", new BlogComment());
        BlogPost blogPost = this.webService.getPostRepo().findById(postId).get();
        model.addAttribute("post", blogPost);
        return "viewcomments";
    }

    @PostMapping("/{postId}/comments")
    public String newComment(@ModelAttribute BlogComment comment, @PathVariable long postId, Model model) {
        BlogPost blogPost = this.webService.getPostRepo().findById(postId).get();
        comment.setBlogPost(blogPost);
        this.webService.getCommentRepo().save(comment);
        model.addAttribute("post", blogPost);
        model.addAttribute("comments", this.webService.getCommentRepo().findByBlogPostPostIDOrderByTimestampAsc(postId));
        model.addAttribute("newcomment", new BlogComment());
        return "viewcomments";
    }

}
