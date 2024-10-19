package com.onlineLearningPlatform.demo.OnlineQuiz;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
public class QuizRequest {
    @NotBlank(message = "quizName should not be blank")
    @NotEmpty(message = "quizName should not be empty")
    private String quizName;
    @NotBlank(message = "quizDescription should not be blank")
    @NotEmpty(message = "quizDescription should not be empty")
    private String quizDescription;
    @NotBlank(message = "quizPassword should not be blank")
    @NotEmpty(message = "quizPassword should not be empty")
    private String quizPassword;
    private LocalDateTime quizStart;
    private LocalDateTime quizEnd;
}
