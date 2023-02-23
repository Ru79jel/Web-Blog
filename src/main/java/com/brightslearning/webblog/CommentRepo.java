package com.brightslearning.webblog;

import org.springframework.data.repository.CrudRepository;

public interface CommentRepo extends CrudRepository<BlogComment, Long> {
}
