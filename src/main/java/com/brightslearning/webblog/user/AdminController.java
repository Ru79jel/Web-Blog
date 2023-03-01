package com.brightslearning.webblog.user;

import com.brightslearning.webblog.user.BlogUser;
import com.brightslearning.webblog.user.UserRepo;
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
    public String refreshAdminPage(@ModelAttribute Iterable<BlogUser> users, Model model){

        model.addAttribute("users", users);

        return "redirect:/userList";
    }
    @GetMapping("/user/{id}")
    public String editUser (@PathVariable long id,Model model){
        BlogUser user = userRepo.findById(id).get();
        model.addAttribute("user",user);
        return "editUser";
    }

    @PostMapping("/user/{id}")
    public String setAdmin (@PathVariable long id, Model model){
        BlogUser user = userRepo.findById(id).get();
        if(user.isAdmin() == false) {
            user.setAdmin(true);
        }
        else {
            user.setAdmin(false);
        }

        userRepo.save(user);
        model.addAttribute("user",user);

        return "editUser";

    }

}
