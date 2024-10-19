package com.onlineLearningPlatform.demo.Course;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CourseResponse {
    private String CourseName;
    private String CourseDescription;
    private String Category;
    private String CourseThumbnail;
    private String ownerName;
    private LocalDateTime createdAt;
}
