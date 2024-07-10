package com.thuannluit.quizzes.controller;

import com.thuannluit.quizzes.entity.Posts;
import com.thuannluit.quizzes.entity.Question;
import com.thuannluit.quizzes.entity.UserQuizHistory;
import com.thuannluit.quizzes.payload.AchievementDto;
import com.thuannluit.quizzes.payload.PostReq;
import com.thuannluit.quizzes.repository.PostRepository;
import com.thuannluit.quizzes.service.IQuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuestionController {
    private final IQuestionService questionService;
    private final PostRepository postRepository;

    @Operation(summary = "Create a new question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Question created successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/create-new-question")
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question) {
        Question createdQuestion = questionService.createQuestion(question);
        return ResponseEntity.status(CREATED).body(createdQuestion);
    }

    @Operation(summary = "Get all questions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all questions", content = @Content)
    })
    @GetMapping("/all-questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @Operation(summary = "Get a question by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the question", content = @Content),
            @ApiResponse(responseCode = "404", description = "Question not found", content = @Content)
    })
    @GetMapping("/question/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Optional<Question> theQuestion = questionService.getQuestionById(id);
        if (theQuestion.isPresent()) {
            return ResponseEntity.ok(theQuestion.get());
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    @Operation(summary = "Update a question by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question updated successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Question not found", content = @Content)
    })
    @PutMapping("/question/{id}/update")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) throws ChangeSetPersister.NotFoundException {
        Question updatedQuestion = questionService.updateQuestion(id, question);
        return ResponseEntity.ok(updatedQuestion);
    }

    @Operation(summary = "Delete a question by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Question deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Question not found", content = @Content)
    })
    @DeleteMapping("/question/{id}/delete")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all subjects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all subjects", content = @Content)
    })
    @GetMapping("/subjects")
    public ResponseEntity<List<String>> getAllSubjects() {
        List<String> subjects = questionService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }

    @Operation(summary = "Fetch questions for a user by subject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found questions for the user", content = @Content),
            @ApiResponse(responseCode = "404", description = "Questions not found", content = @Content)
    })
    @GetMapping("/quiz/fetch-questions-for-user")
    public ResponseEntity<List<Question>> getQuestionsForUser(@RequestParam Integer numOfQuestions, @RequestParam String subject) {
        List<Question> allQuestions = questionService.getQuestionsForUser(numOfQuestions, subject);

        List<Question> mutableQuestions = new ArrayList<>(allQuestions);
        Collections.shuffle(mutableQuestions);

        int availableQuestions = Math.min(numOfQuestions, mutableQuestions.size());
        List<Question> randomQuestions = mutableQuestions.subList(0, availableQuestions);
        return ResponseEntity.ok(randomQuestions);
    }

    @Operation(summary = "Save achievements for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Achievements saved successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/achievement-for-user")
    public ResponseEntity<UserQuizHistory> saveAchievementsForUser(@Valid @RequestBody AchievementDto achievementDto) {
        UserQuizHistory uqh = questionService.saveAchievementsForUser(achievementDto);
        return ResponseEntity.status(CREATED).body(uqh);
    }

    @Operation(summary = "Get achievements for a user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found achievements for the user", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/achievement-for-user/{userName}")
    public ResponseEntity<List<AchievementDto>> getAchievementsForUser(@PathVariable String userName) {
        List<AchievementDto> uqh = questionService.getAchievementsForUser(userName);
        return ResponseEntity.status(CREATED).body(uqh);
    }

    @PostMapping("/posts")
    public ResponseEntity<Posts> createPost(@RequestBody PostReq postReq) {
        try {
            Posts p = postRepository
                    .save(new Posts(postReq.getTitle(), postReq.getContent()));
            return new ResponseEntity<>(p, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
