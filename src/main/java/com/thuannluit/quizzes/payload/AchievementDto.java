package com.thuannluit.quizzes.payload;

import lombok.Data;

import java.util.Date;
import java.util.Optional;

@Data
public class AchievementDto {
    private String userName;
    private String quizSubjects;
    private Integer totalScore;
    Optional<String> completeAt;
}
