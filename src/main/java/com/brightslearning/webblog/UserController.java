package com.brightslearning.webblog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    private UserRepo userRepo;

    @Autowired
    public UserController(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("registration",new RegisterUser("","",""));
        return "register";
    }


    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registration") RegisterUser registration, BindingResult bindingResult) {
        if (!registration.getPassword1().equals(registration.getPassword2())) {
            bindingResult.addError(new FieldError("registration", "password2", "Passwords are not matching"));
        }

        if (userRepo.existsByUserName(registration.getUsername())) {
            bindingResult.addError(new FieldError("registration", "username", "This UserName does already exist"));
        }

        if (bindingResult.hasErrors()){
            return "register";

    }
            BlogUser user = new BlogUser(registration.getUsername(), registration.getPassword1());
        userRepo.save(user);

        return "redirect:/login";
    }
}
