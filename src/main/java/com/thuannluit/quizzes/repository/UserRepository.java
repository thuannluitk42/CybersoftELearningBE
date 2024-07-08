package com.thuannluit.quizzes.repository;

import com.thuannluit.quizzes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u from User u where (lower(u.username) like lower(:username))")
    User findByUsername(String username);
}
