package com.onlineLearningPlatform.demo.Course;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.onlineLearningPlatform.demo.Common.Base;
import com.onlineLearningPlatform.demo.Lecture.Lecture;
import com.onlineLearningPlatform.demo.User.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Course extends Base {
    private String CourseName;
    private String CourseDescription;
    private String Category;
    private String CourseThumbnail;
    @ManyToMany
    private List<User> allregistereduser;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User owner;
    @OneToMany(mappedBy = "course")
    @JsonManagedReference
    private List<Lecture> lectures;
}
