package com.brightslearning.webblog.comment;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CommentRepo extends CrudRepository<BlogComment, Long> {
   List<BlogComment> findByBlogPostPostIDOrderByTimestampAsc(long postId);
   List<BlogComment> deleteBlogCommentsByBlogPostPostID(long postId);
}
