package com.brightslearning.webblog.post;

import com.brightslearning.webblog.user.BlogUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
public class PostController {
    private PostRepo postRepo;


    @Autowired
    public PostController(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    @GetMapping("/")
    public String showBlogPosts(Model model, @ModelAttribute("sessionUser") BlogUser sessionUser) {
        model.addAttribute("posts", this.postRepo.findByOrderByTimestampDesc());
        model.addAttribute("newpost", new BlogPost());
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