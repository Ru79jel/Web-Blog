package com.brightslearning.webblog.post;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepo extends CrudRepository<BlogPost,Long> {
    List<BlogPost> findByOrderByTimestampDesc();
}
