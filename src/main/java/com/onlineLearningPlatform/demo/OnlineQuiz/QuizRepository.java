package com.onlineLearningPlatform.demo.OnlineQuiz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuizRepository extends JpaRepository<Quiz,Integer> {
    @Query("""
      select  q  from Quiz q  where q.Course.id=:courseid
""")
    Page<Quiz> findallQuizbycourse(Pageable pageable, int courseid);
}
