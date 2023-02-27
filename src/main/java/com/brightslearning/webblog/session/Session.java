package com.brightslearning.webblog.session;

import com.brightslearning.webblog.user.BlogUser;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class Session {
    @Id
    private String id = UUID.randomUUID().toString();
    @ManyToOne
    private BlogUser blogUser;
    private Instant expiresAt;

    public Session(BlogUser blogUser, Instant expiresAt) {
        this.blogUser = blogUser;
        this.expiresAt = expiresAt;
    }
}
