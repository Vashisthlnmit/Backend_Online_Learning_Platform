package com.onlineLearningPlatform.demo.OnlineQuiz;


import com.onlineLearningPlatform.demo.Common.Base;
import com.onlineLearningPlatform.demo.Course.Course;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Quiz extends Base {
    private String quizName;
    private String quizDescription;
    private String quizPassword;
    private LocalDateTime quizStart;
    private LocalDateTime quizEnd;
    @ManyToOne()
    private Course Course;

}
