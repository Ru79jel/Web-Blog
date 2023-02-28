package com.brightslearning.webblog.post;

import com.brightslearning.webblog.comment.BlogComment;
import com.brightslearning.webblog.comment.CommentRepo;
import com.brightslearning.webblog.user.BlogUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PostController {
    private PostRepo postRepo;
    private CommentRepo commentRepo;


    @Autowired
    public PostController(PostRepo postRepo, CommentRepo commentRepo) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
    }

    @GetMapping("/")
    public String showBlogPosts(Model model, @ModelAttribute("sessionUser") BlogUser sessionUser) {

//        System.out.println(this.postRepo.findByOrderByTimestampDesc().toString());
        // Why do posts not contain the full "deep" copy of every element? In larger scenarios this would lead to an absolute
        // messy chaos! Anyway: It should be doable somehow - so this is just a late-night workaround!

        // get all posts
        List<BlogPost> posts = this.postRepo.findByOrderByTimestampDesc();

        // determine the number of comments for each post and save them in a Map
        Map<BlogPost, Integer> countedBlogPosts = new HashMap<>();
        for (BlogPost p : posts) {
            List<BlogComment> comments = this.commentRepo.findByBlogPostPostIDOrderByTimestampAsc(p.getPostID());
            countedBlogPosts.put(p,comments.size());
        }

        // hand over the Map as a model attribute

//        model.addAttribute("posts", this.postRepo.findByOrderByTimestampDesc());   // replaced that by the postsMap!
        model.addAttribute("newpost", new BlogPost());
        model.addAttribute("postsMap", countedBlogPosts);
        return "index";
    }

    @PostMapping("/")
    public String newBlogPost(@ModelAttribute BlogPost post, @ModelAttribute("sessionUser") BlogUser sessionUser,
                              Model model) {
        post.setPostOwner(sessionUser);
        this.postRepo.save(post);
        model.addAttribute("posts", this.postRepo.findByOrderByTimestampDesc());
        model.addAttribute("newpost", new BlogPost());
        return "index";
    }

    @GetMapping("/posts/edit/{id}")
    public String editComment(@ModelAttribute("sessionUser") BlogUser sessionUser, @PathVariable long id, Model model) {
        if (this.postRepo.findById(id).isPresent()) {
            if (sessionUser.isAdmin()) {
                model.addAttribute("postToEdit", this.postRepo.findById(id).get());
                return "editpost";
            } else return "redirect:/";
        }
        return "errorpage";
    }

    @PostMapping("/posts/edit/{id}")
    public String editComment(@ModelAttribute BlogPost post, @ModelAttribute("sessionUser") BlogUser sessionUser,
                              @PathVariable long id) {
        if (this.postRepo.findById(id).isPresent()) {
            if (sessionUser.isAdmin()) {
                BlogPost postToEdit = this.postRepo.findById(id).get();
                postToEdit.setTitle(post.getTitle());
                postToEdit.setMessage(post.getMessage());
                postToEdit.setLastEditAt(LocalDate.now());
                this.postRepo.save(postToEdit);
            }
            return "redirect:/";
        }
        return "errorpage";
    }

    @GetMapping("/posts/delete/{postId}")
    public String deletePost(@ModelAttribute("sessionUser") BlogUser sessionUser, @PathVariable long postId) {
        if (sessionUser.isAdmin()) {
            this.postRepo.deleteById(postId);
        }
        return "redirect:/";
    }
}
