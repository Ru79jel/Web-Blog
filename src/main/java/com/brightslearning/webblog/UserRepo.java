package com.brightslearning.webblog;

import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<BlogUser,Long> {

    boolean existsByUserName(String userName);
}
