package com.onlineLearningPlatform.demo.Course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Integer>{

  @Query("""
      Select c  from Course c where c.id=:courseId and c.owner.id=:userId
""")
  Optional<Course> findByIdandUser(int courseId, Integer userId);

    @Query("""
        Select c  from Course c
""")
    Page<Course> findAllcourse(Pageable pageable);
}
