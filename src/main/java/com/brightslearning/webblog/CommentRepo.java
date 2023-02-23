package com.brightslearning.webblog;

import org.springframework.data.repository.CrudRepository;

import javax.xml.stream.events.Comment;
import java.util.List;

public interface CommentRepo extends CrudRepository<BlogComment, Long> {
   List<BlogComment> findByBlogPostPostID(long postid);
}
