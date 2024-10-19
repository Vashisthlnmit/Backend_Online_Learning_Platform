package com.onlineLearningPlatform.demo.Course;

import org.springframework.stereotype.Service;

@Service
public class CourseMapper {
    public Course toCourse(CourseRequest request,String file){
        return Course.builder()
                .CourseName(request.getCourseName())
                .CourseDescription(request.getCourseDescription())
                .Category(request.getCategory())
                .CourseThumbnail(file)
                .build();
    }
    public CourseResponse toCourseResponse(Course course){
        return CourseResponse.builder()
                .CourseName(course.getCourseName())
                .CourseDescription(course.getCourseDescription())
                .Category(course.getCategory())
                .ownerName(course.getOwner().getUsername())
                .CourseThumbnail(course.getCourseThumbnail())
                .createdAt(course.getCreationDate())
                .build();
    }
}
