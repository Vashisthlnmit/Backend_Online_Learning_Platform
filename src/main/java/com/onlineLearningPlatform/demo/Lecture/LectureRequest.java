package com.onlineLearningPlatform.demo.Lecture;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class LectureRequest {
    @NotBlank(message = "LectureTitle is mandatory")
    @NotEmpty(message = "LectureTitle is mandatory")
    private String LectureTitle;
    @NotBlank(message = "LectureDescription is mandatory")
    @NotEmpty(message = "LectureDescription is mandatory")
    private String LectureDescription;
    //@NotBlank(message = "LectureUrl is mandatory")
    //@NotEmpty(message = "LectureUrl is mandatory")
    private MultipartFile LectureUrl;
    //@NotBlank(message = "LectureThumbnail is mandatory")
    //@NotEmpty(message = "LectureThumbnail is mandatory")
    private MultipartFile lectureThumbnail;
}
