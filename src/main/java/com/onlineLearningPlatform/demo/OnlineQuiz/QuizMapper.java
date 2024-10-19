package com.onlineLearningPlatform.demo.OnlineQuiz;

import com.onlineLearningPlatform.demo.Course.Course;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuizMapper {
    private final PasswordEncoder passwordEncoder;
    public Quiz toQuiz(QuizRequest quizRequest, Course course) {
        return Quiz.builder()
                .quizName(quizRequest.getQuizName())
                .quizDescription(quizRequest.getQuizDescription())
                .quizPassword(passwordEncoder.encode(quizRequest.getQuizPassword()))
                .quizStart(quizRequest.getQuizStart())
                .quizEnd(quizRequest.getQuizEnd())
                .Course(course)
                .build();
    }
    public QuizResponse toQuizResponse(Quiz quiz) {
        return QuizResponse.builder().
                quizName(quiz.getQuizName()).
                quizDescription(quiz.getQuizDescription()).
                quizStart(quiz.getQuizStart()).
                quizEnd(quiz.getQuizEnd()).
                build();
    }
}
