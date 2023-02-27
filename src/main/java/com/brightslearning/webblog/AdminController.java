package com.brightslearning.webblog;

import com.brightslearning.webblog.BlogUser;
import com.brightslearning.webblog.UserRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@NoArgsConstructor
@Controller
public class AdminController {
    private UserRepo userRepo;
    @Autowired
    public AdminController(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @GetMapping("/userlist")
    public String showAdminPage(Model model){
        Iterable<BlogUser> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "userList";
    }

    @PostMapping("/userlist")
    public String refreshAdminPage(@ModelAttribute Model model){
        Iterable<BlogUser> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "redirect:/userList";
    }

}
