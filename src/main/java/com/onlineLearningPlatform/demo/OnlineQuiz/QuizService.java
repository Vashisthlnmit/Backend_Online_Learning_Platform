package com.onlineLearningPlatform.demo.OnlineQuiz;

import com.onlineLearningPlatform.demo.Common.PageResponse;
import com.onlineLearningPlatform.demo.Course.Course;
import com.onlineLearningPlatform.demo.Course.CourseRepository;
import com.onlineLearningPlatform.demo.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final CourseRepository courseRepository;
    private final  QuizMapper QuizMapper;
    public String CreateQuiz(QuizRequest QuizRequest,Integer Courseid, Authentication connecteduser) {
        Course course = courseRepository.findById(Courseid).orElseThrow(()->new RuntimeException("no such course found"));
        User user=((User) connecteduser.getPrincipal());
        //System.out.println(user.getId());
        System.out.println(course.getOwner());
        if(!Objects.equals(course.getOwner().getId(), user.getId())){
            throw new RuntimeException("you are not owner of this course hence you cannot create course");
        }
        if(QuizRequest.getQuizStart().isAfter(QuizRequest.getQuizEnd())){
            throw  new RuntimeException("quiz start time is after end time");
        }
        Quiz quiz=QuizMapper.toQuiz(QuizRequest,course);
        quizRepository.save(quiz);
        return "Quiz has created successfully";
    }
    public String DeleteQuiz(Integer quizid,Authentication connecteduser) {
        Quiz quiz=quizRepository.findById(quizid).orElse(null);
        if(quiz==null){
            throw new RuntimeException("no such quiz found");
        }
        User user=((User) connecteduser.getPrincipal());
        if(!Objects.equals(quiz.getCourse().getOwner().getId(),user.getId())){
            throw new RuntimeException("you are not owner of this course hence you cannot delete course");
        }
        quizRepository.deleteById(quiz.getId());
        return "Quiz has deleted successfully";
    }

    public String UpdateQuiz(QuizRequest quizrequest, int quizid, Authentication connectedUser) {
        Quiz quiz=quizRepository.findById(quizid).orElse(null);
        User user=((User) connectedUser.getPrincipal());
        if(!Objects.equals(quiz.getCourse().getOwner(),user)){
            throw new RuntimeException("you are not owner of this course hence you cannot delete course");
        }
        quiz.setQuizName(quizrequest.getQuizName()!=null?quizrequest.getQuizName():quiz.getQuizName());
        quiz.setQuizDescription(quizrequest.getQuizDescription()!=null?quizrequest.getQuizDescription():quiz.getQuizDescription());
        quiz.setQuizPassword(quizrequest.getQuizPassword()!=null ? quizrequest.getQuizPassword():quiz.getQuizPassword());
        quiz.setQuizStart(quizrequest.getQuizStart()!=null?quizrequest.getQuizStart():quiz.getQuizStart());
        quiz.setQuizEnd(quizrequest.getQuizEnd()!=null?quizrequest.getQuizEnd():quiz.getQuizEnd());
        quizRepository.save(quiz);
        return null;
    }
    public PageResponse<?> GetallQuiz(int courseid, int page, int size) {
        Pageable pageable= PageRequest.of(page,size, Sort.by("creationDate").descending());
        System.out.println("working fine");
        Page<Quiz> allquiz=quizRepository.findallQuizbycourse(pageable,courseid);
        List<QuizResponse> AllQuizResponse=allquiz
                .stream()
                .map(QuizMapper::toQuizResponse)
                .toList();
        return new PageResponse<>(
                AllQuizResponse,
                allquiz.getNumber(),
                allquiz.getSize(),
                allquiz.getTotalElements(),
                allquiz.getTotalPages(),
                allquiz.isFirst(),
                allquiz.isLast()
        );
    }
}