package com.brightslearning.webblog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Struct;
import java.time.LocalDate;

@Controller
public class WebController {

    private WebService webService;

    @Autowired
    public WebController(WebService webService) {
        this.webService = webService;

    }

    @GetMapping("/createTestdata")
    public @ResponseBody String createTestDate() {
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
            post.setPostOwner(this.webService.getUserRepo().findById((long) i).get());
            this.webService.getPostRepo().save(post);

        }
        return "testdate created";
    }

    @GetMapping("/")
    public String showBlogBlogPosts(Model model, @ModelAttribute("sessionUser") BlogUser sessionUser) {
        model.addAttribute("posts", this.webService.getPostRepo().findByOrderByTimestampDesc());
        model.addAttribute("newpost", new BlogPost());
        return "index";
    }

    @PostMapping("/")
    public String newBlogPost(@ModelAttribute BlogPost post, @ModelAttribute("sessionUser") BlogUser sessionUser, Model model) {
        post.setPostOwner(sessionUser);
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
    public String newComment(@ModelAttribute BlogComment comment, @ModelAttribute("sessionUser") BlogUser sessionUser, @PathVariable long postId, Model model) {
        BlogPost blogPost = this.webService.getPostRepo().findById(postId).get();
        comment.setBlogPost(blogPost);
        comment.setBlogUser(sessionUser);
        this.webService.getCommentRepo().save(comment);
        model.addAttribute("post", blogPost);
        model.addAttribute("comments", this.webService.getCommentRepo().findByBlogPostPostIDOrderByTimestampAsc(postId));
        model.addAttribute("newcomment", new BlogComment());
        return "viewcomments";
    }

    @GetMapping("/comments/delete/{id}")
    public String deleteComment(@ModelAttribute("sessionUser") BlogUser sessionUser, @PathVariable long id) {
        BlogComment comment = this.webService.getCommentRepo().findById(id).get();
        if(sessionUser.isAdmin() || sessionUser.equals(comment.getBlogUser())) {
            this.webService.getCommentRepo().deleteById(id);
        }
        return "redirect:" + UriComponentsBuilder.fromPath("/").pathSegment(String.valueOf(comment.getBlogPost().getPostID())).pathSegment("comments").toUriString();
    }

    @GetMapping("/posts/edit/{id}")
    public String editComment(@ModelAttribute("sessionUser") BlogUser sessionUser, @PathVariable long id, Model model) {
        if(sessionUser.isAdmin()) {
            model.addAttribute("postToEdit",this.webService.getPostRepo().findById(id).get());
            return "editPost";
        } else  return "redirect:/";
    }
    @PostMapping("/posts/edit/{id}")
    public String editComment(@ModelAttribute BlogPost post, @ModelAttribute("sessionUser") BlogUser sessionUser, @PathVariable long id) {
        if(sessionUser.isAdmin()) {
            BlogPost postToEdit = this.webService.getPostRepo().findById(id).get();
            postToEdit.setTitle(post.getTitle());
            postToEdit.setMessage(post.getMessage());
            postToEdit.setLastEditAt(LocalDate.now());
            this.webService.getPostRepo().save(postToEdit);
        }
        return "redirect:/";
    }

    @GetMapping("/posts/delete/{postId}")
    public String deletePost(@ModelAttribute("sessionUser") BlogUser sessionUser, @PathVariable long postId) {
        if(sessionUser.isAdmin()) {
            this.webService.getPostRepo().deleteById(postId);
        }
        return "redirect:/";
    }



}
