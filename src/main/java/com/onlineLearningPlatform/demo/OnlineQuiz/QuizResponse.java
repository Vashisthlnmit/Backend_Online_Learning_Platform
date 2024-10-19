package com.onlineLearningPlatform.demo.OnlineQuiz;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class QuizResponse {
    private String quizName;
    private String quizDescription;
    private LocalDateTime quizStart;
    private LocalDateTime quizEnd;
}
