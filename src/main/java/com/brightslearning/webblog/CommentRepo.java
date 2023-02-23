package com.brightslearning.webblog;

import org.attoparser.dom.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepo extends CrudRepository<BlogComment, Long> {

    List<BlogComment> findByOrderByTimestampAsc();
}
