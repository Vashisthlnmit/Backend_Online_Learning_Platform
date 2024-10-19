package com.onlineLearningPlatform.demo.Lecture;

import com.onlineLearningPlatform.demo.Course.Course;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LectureResponse {
    private String LectureTitle;
    private String LectureDescription;
    private String LectureUrl;
    private String LectureThumbnail;
    private Course course;
}
