package com.brightslearning.webblog.comment;

import com.brightslearning.webblog.post.BlogPost;
import com.brightslearning.webblog.post.PostRepo;
import com.brightslearning.webblog.user.BlogUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class CommentController {

    private PostRepo postRepo;
    private CommentRepo commentRepo;


    @Autowired
    public CommentController(PostRepo postRepo, CommentRepo commentRepo) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
    }

    @GetMapping("/{postId}/comments")
    public String showComments(@PathVariable long postId, Model model) {
        if (this.postRepo.findById(postId).isPresent()) {
            model.addAttribute("comments", this.commentRepo.findByBlogPostPostIDOrderByTimestampAsc(postId));
            model.addAttribute("newcomment", new BlogComment());
            BlogPost blogPost = this.postRepo.findById(postId).get();
            model.addAttribute("post", blogPost);
            return "viewcomments";
        }
        return "errorpage";
    }

    @PostMapping("/{postId}/comments")
    public String newComment(@ModelAttribute BlogComment comment, @ModelAttribute("sessionUser") BlogUser sessionUser, @PathVariable long postId, Model model) {
        if (this.postRepo.findById(postId).isPresent()) {
            BlogPost blogPost = this.postRepo.findById(postId).get();
            comment.setBlogPost(blogPost);
            comment.setBlogUser(sessionUser);
            this.commentRepo.save(comment);
            model.addAttribute("post", blogPost);
            model.addAttribute("comments", this.commentRepo.findByBlogPostPostIDOrderByTimestampAsc(postId));
            model.addAttribute("newcomment", new BlogComment());
            return "viewcomments";
        }
        return "errorpage";
    }

    @GetMapping("/comments/delete/{id}")
    public String deleteComment(@ModelAttribute("sessionUser") BlogUser sessionUser, @PathVariable long id) {
        if (this.commentRepo.findById(id).isPresent()) {
            BlogComment comment = this.commentRepo.findById(id).get();
            if (sessionUser.isAdmin() || sessionUser.equals(comment.getBlogUser())) {
                this.commentRepo.deleteById(id);
            }
            return "redirect:" +
                    UriComponentsBuilder
                            .fromPath("/")
                            .pathSegment(String.valueOf(comment.getBlogPost().getPostID()))
                            .pathSegment("comments")
                            .toUriString();
        }
        return "errorpage";
    }
}
