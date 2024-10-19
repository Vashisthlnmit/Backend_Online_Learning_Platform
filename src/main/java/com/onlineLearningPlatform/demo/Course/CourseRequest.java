package com.onlineLearningPlatform.demo.Course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class CourseRequest {
    @NotBlank(message = "CourseName should not be empty")
    @NotEmpty(message = "CourseName should not be empty")
    private String CourseName;
    @NotBlank(message = "CourseDescription should not be empty")
    @NotEmpty(message = "CourseDescription should not be empty")
    private String CourseDescription;
    @NotBlank(message = "Category should not be empty")
    @NotEmpty(message = "Category should not be empty")
    private String Category;
   // @NotBlank(message = "CourseImage should not be empty")
    //@NotEmpty(message = "CourseImage should not be empty")
    private MultipartFile CourseImage;
}
