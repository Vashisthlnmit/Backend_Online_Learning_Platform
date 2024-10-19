package com.onlineLearningPlatform.demo.Course;

import com.onlineLearningPlatform.demo.Common.PageResponse;
import com.onlineLearningPlatform.demo.File.FileUploadService;
import com.onlineLearningPlatform.demo.User.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final  CourseMapper courseMapper;
    private final FileUploadService fileUploadService;
    public String SaveCourse(CourseRequest request, Authentication connectUser) throws OperationNotSupportedException {
        // not coming here
        //System.out.println("coming into the course");
        User user =((User) connectUser.getPrincipal());
//        boolean isAllowed= Objects.equals();
//        if(!isAllowed){
//            throw new OperationNotSupportedException("your are not admin so ");
//        }
        System.out.println(request.getCourseImage());
        String courseThumbnailfile=fileUploadService.saveFile(request.getCourseImage(),-1);
        Course course=courseMapper.toCourse(request,courseThumbnailfile);
        course.setOwner(user);
        courseRepository.save(course);
        return "course Added successfully";
    }
    public CourseResponse  GetoneCourse(int courseId, Authentication connectUser) {
        User user=((User) connectUser.getPrincipal());
        Course course=courseRepository.findById(courseId).orElseThrow(
                ()->new IllegalStateException("course does not exist")
        );
        CourseResponse courseResponse=courseMapper.toCourseResponse(course);
        return courseResponse;
    }
    public String DeleteoneCourse(int courseId, Authentication connectUser) {
        User user=((User) connectUser.getPrincipal());
        Course course=courseRepository.findByIdandUser(courseId,user.getId()).orElseThrow(
                ()->new IllegalStateException("no such course of your exist")
        );
        courseRepository.deleteById(courseId);
        return "course Deleted successfully";
    }

    public String UpdateCourse(int courseId, @Valid CourseRequest request, Authentication connectUser) {
        User user=((User) connectUser.getPrincipal());
        Course course=courseRepository.findByIdandUser(courseId,user.getId()).orElseThrow(
                ()->new IllegalStateException("no such course of your exist")
        );
        String thumbnailurl=fileUploadService.saveFile(request.getCourseImage(),courseId);
        course.setCourseName(request.getCourseName());
        course.setCourseDescription(request.getCourseDescription());
        course.setCategory(request.getCategory());
        courseRepository.save(course);
        return "Course Updated Successfully";
    }

    public PageResponse<CourseResponse> getcourse(int page, int size) {
        Pageable pageable= PageRequest.of(page,size, Sort.by("creationDate").descending());
        //System.out.println("working fine till here");
        Page<Course> courses=courseRepository.findAllcourse(pageable);
        //System.out.println("working fine till here 2");
        List<CourseResponse> courseList=courses.stream()
                .map(courseMapper::toCourseResponse)
                .toList();
        //System.out.println("working fine till here 3");
        return new PageResponse<>(
                courseList,
                courses.getNumber(),
                courses.getSize(),
                courses.getTotalElements(),
                courses.getTotalPages(),
                courses.isFirst(),
                courses.isLast()
        );
    }
}
