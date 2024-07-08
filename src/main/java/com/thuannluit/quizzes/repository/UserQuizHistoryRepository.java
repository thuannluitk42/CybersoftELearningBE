package com.thuannluit.quizzes.repository;

import com.thuannluit.quizzes.entity.UserQuizHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserQuizHistoryRepository extends JpaRepository<UserQuizHistory, Long> {
    @Query("SELECT u from UserQuizHistory u where (lower(u.username) like lower(:username))")
    List<UserQuizHistory> getUserQuizHistoriesByUsername(String username);
}
