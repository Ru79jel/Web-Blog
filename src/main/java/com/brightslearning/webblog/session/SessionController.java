package com.brightslearning.webblog.session;

import com.brightslearning.webblog.BlogUser;
import com.brightslearning.webblog.UserRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import java.time.Instant;
import java.util.Optional;


@Controller
public class SessionController {
    private UserRepo userRepo;
    private SessionRepo sessionRepo;

    @Autowired
    public SessionController(UserRepo userRepo, SessionRepo sessionRepo) {
        this.userRepo = userRepo;
        this.sessionRepo = sessionRepo;
    }

    @GetMapping("/login")
    public String loginUser(Model model) {
        model.addAttribute("blogUser", new BlogUser());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("blogUser") BlogUser blogUser, BindingResult bindingResult, HttpServletResponse response) {
        Optional <BlogUser> userOptional = userRepo.findByUserNameAndPassword(blogUser.getUserName(),blogUser.getPassword());
        if(userOptional.isPresent()) {
            Session session = new Session(userOptional.get(), Instant.now().plusSeconds(7*24*60*60));
            sessionRepo.save(session);
            Cookie cookie = new  Cookie("sessionId", session.getId());
            response.addCookie(cookie);
            return "redirect:/";
        }
        bindingResult.addError(new FieldError("blogUser", "password", "Login not successful"));
    return "login";
    }

    @PostMapping("/logout")
    public String logout(@CookieValue(value = "sessionId", defaultValue = "") String sessionId, HttpServletResponse response) {
        Optional<Session> optionalSession = sessionRepo.findByIdAndExpiresAtAfter(sessionId, Instant.now());
        optionalSession.ifPresent(session -> sessionRepo.delete(session));

        Cookie cookie = new Cookie("sessionId", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }
}
