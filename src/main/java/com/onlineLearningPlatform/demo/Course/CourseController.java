package com.onlineLearningPlatform.demo.Course;

import com.onlineLearningPlatform.demo.Common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.naming.OperationNotSupportedException;

@RestController
@RequestMapping("/Course")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    @PostMapping(value = "/addCourse",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> AddCourse(@ModelAttribute @Valid CourseRequest request , Authentication connectUser) throws OperationNotSupportedException {
        System.out.println(request.getCourseImage());
        return ResponseEntity.ok(courseService.SaveCourse(request,connectUser));
    }
    @GetMapping("/getoneCourse/{courseId}")
    public ResponseEntity<CourseResponse> GetOneCourse(@PathVariable int courseId,Authentication connectUser) throws OperationNotSupportedException {
        System.out.println(courseId);
        return ResponseEntity.ok(courseService.GetoneCourse(courseId,connectUser));
    }
    @DeleteMapping("/deleteCourse/{courseId}")
    public ResponseEntity<?> DeleteoneCourse(@PathVariable int courseId,Authentication connectUser) throws OperationNotSupportedException {
        System.out.println(courseId);
        return ResponseEntity.ok(courseService.DeleteoneCourse(courseId,connectUser));
    }
    @PatchMapping("/updateCourse/{courseId}")
    public ResponseEntity<?> UpdateoneCourse(@PathVariable int courseId,@ModelAttribute @Valid CourseRequest request,Authentication connectUser) throws OperationNotSupportedException {
        System.out.println(request);
        return ResponseEntity.ok(courseService.UpdateCourse(courseId,request,connectUser));
    }
    @GetMapping("/getAllcourse")
    public ResponseEntity<PageResponse<CourseResponse>> GetAllCourse(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size",defaultValue = "5",required = false) int size
    ){
        //System.out.println(page);
        //System.out.println(size);
        return ResponseEntity.ok(courseService.getcourse(page,size));
    }


}
