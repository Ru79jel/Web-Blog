package com.brightslearning.webblog;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<BlogUser,Long> {

    boolean existsByUserName(String userName);
    Optional <BlogUser> findByUserNameAndPassword(String username, String password);
}
