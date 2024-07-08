package com.thuannluit.quizzes.service;

import com.thuannluit.quizzes.entity.Question;
import com.thuannluit.quizzes.entity.UserQuizHistory;
import com.thuannluit.quizzes.payload.AchievementDto;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

public interface IQuestionService {

    Question createQuestion(Question question);

    List<Question> getAllQuestions();

    Optional<Question> getQuestionById(Long id);

    List<String> getAllSubjects();

    Question updateQuestion(Long id, Question question) throws  ChangeSetPersister.NotFoundException;

    void  deleteQuestion(Long id);

    List<Question> getQuestionsForUser(Integer numOfQuestions, String subject);

    UserQuizHistory saveAchievementsForUser(AchievementDto achievementDto);

    List<AchievementDto> getAchievementsForUser(String userName);
}
