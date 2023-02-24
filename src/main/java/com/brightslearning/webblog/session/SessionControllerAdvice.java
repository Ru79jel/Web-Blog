package com.brightslearning.webblog.session;

import com.brightslearning.webblog.BlogUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.Instant;
import java.util.Optional;

@ControllerAdvice
public class SessionControllerAdvice {
    private SessionRepo sessionRepo;

    @Autowired
    public SessionControllerAdvice(SessionRepo sessionRepo) {
        this.sessionRepo = sessionRepo;
    }

    @ModelAttribute("sessionUser")
    public BlogUser sessionUser(@CookieValue(value = "sessionId", defaultValue = "") String sessionId) {
        if (!sessionId.isEmpty()) {
            Optional<Session> optionalSession = sessionRepo.findByIdAndExpiresAtAfter(
                    sessionId, Instant.now());
            if (optionalSession.isPresent()) {
                Session session = optionalSession.get();

                // new expiresAt value for the current session
                session.setExpiresAt(Instant.now().plusSeconds(7 * 24 * 60 * 60));
                sessionRepo.save(session);

                return session.getBlogUser();
            }
        }
        return null;
    }
}
