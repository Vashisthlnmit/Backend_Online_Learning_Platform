package com.onlineLearningPlatform.demo.Lecture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LectureRepository extends JpaRepository<Lecture,Integer> {
    @Query("""
       Select l from Lecture l  where l.course.id=:courseId
""")
    Page<Lecture> findAllbyCourse(Pageable pageable, int courseId);
}
