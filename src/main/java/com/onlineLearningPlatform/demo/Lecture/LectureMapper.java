package com.onlineLearningPlatform.demo.Lecture;

import org.springframework.stereotype.Service;

@Service
public class LectureMapper {

    public LectureResponse toLectureResponse(Lecture lecture) {
        return LectureResponse.builder()
                .LectureTitle(lecture.getLectureTitle())
                .LectureDescription(lecture.getLectureDescription())
                .LectureUrl(lecture.getLectureUrl())
                .LectureThumbnail(lecture.getLectureThumbnail())
                .course(lecture.getCourse())
                .build();
    }
}
