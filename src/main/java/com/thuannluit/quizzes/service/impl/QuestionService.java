package com.thuannluit.quizzes.service.impl;

import com.thuannluit.quizzes.entity.Question;
import com.thuannluit.quizzes.entity.User;
import com.thuannluit.quizzes.entity.UserQuizHistory;
import com.thuannluit.quizzes.payload.AchievementDto;
import com.thuannluit.quizzes.repository.QuestionRepository;
import com.thuannluit.quizzes.repository.UserQuizHistoryRepository;
import com.thuannluit.quizzes.repository.UserRepository;
import com.thuannluit.quizzes.service.IQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    UserQuizHistoryRepository userQuizHistoryRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<String> getAllSubjects() {
        return questionRepository.findDistinctSubject();
    }

    @Override
    public Question updateQuestion(Long id, Question question) throws ChangeSetPersister.NotFoundException {
        Optional<Question> theQuestion = this.getQuestionById(id);
        if (theQuestion.isPresent()){
            Question updatedQuestion = theQuestion.get();
            updatedQuestion.setQuestion(question.getQuestion());
            updatedQuestion.setChoices(question.getChoices());
            updatedQuestion.setCorrectAnswers(question.getCorrectAnswers());
            return questionRepository.save(updatedQuestion);
        }else {
            throw new ChangeSetPersister.NotFoundException();
        }

    }
    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
    @Override
    public List<Question> getQuestionsForUser(Integer numOfQuestions, String subject) {
        Pageable pageable = PageRequest.of(0, numOfQuestions);
        return questionRepository.findBySubject(subject, pageable).getContent();
    }

    @Override
    public UserQuizHistory saveAchievementsForUser(AchievementDto achievementDto) {
        User u = userRepository.findByUsername(achievementDto.getUserName());

        UserQuizHistory userQuizHistory = new UserQuizHistory();

        userQuizHistory.setUsername(u.getUsername());
        userQuizHistory.setQuizSubjects(achievementDto.getQuizSubjects());
        userQuizHistory.setScore(achievementDto.getTotalScore());
        Date date = new Date();
        userQuizHistory.setCompletedAt(date);

        return userQuizHistoryRepository.save(userQuizHistory);
    }

    @Override
    public List<AchievementDto> getAchievementsForUser(String userName) {
        List<UserQuizHistory> quizHistories = userQuizHistoryRepository.getUserQuizHistoriesByUsername(userName);
        List<AchievementDto> achievementDtos = new ArrayList<>();
        for (UserQuizHistory userQuizHistory:quizHistories) {

            AchievementDto achievementDto = new AchievementDto();
            achievementDto.setUserName(userQuizHistory.getUsername());
            achievementDto.setQuizSubjects(userQuizHistory.getQuizSubjects());
            achievementDto.setTotalScore(userQuizHistory.getScore());

            achievementDto.setCompleteAt(Optional.ofNullable(String.valueOf(userQuizHistory.getCompletedAt())));

            achievementDtos.add(achievementDto);
        }

        return achievementDtos;
    }
}
