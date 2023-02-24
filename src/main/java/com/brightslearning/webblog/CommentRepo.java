package com.brightslearning.webblog;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CommentRepo extends CrudRepository<BlogComment, Long> {
   List<BlogComment> findByBlogPostPostIDOrderByTimestampAsc(long postid);

}
