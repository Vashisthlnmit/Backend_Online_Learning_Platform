package com.onlineLearningPlatform.demo.Lecture;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onlineLearningPlatform.demo.Common.Base;
import com.onlineLearningPlatform.demo.Course.Course;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lecture extends Base {
    private String LectureTitle;
    private String LectureDescription;
    private String LectureUrl;
    private String LectureThumbnail;
    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;
}
