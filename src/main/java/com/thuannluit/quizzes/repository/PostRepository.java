package com.thuannluit.quizzes.repository;

import com.thuannluit.quizzes.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Posts, Long> {
}
