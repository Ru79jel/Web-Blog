package com.brightslearning.webblog.session;

import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.Optional;

public interface SessionRepo extends CrudRepository<Session,String> {
    Optional<Session> findByIdAndExpiresAtAfter(String sessionId, Instant now);
}
