package com.onlineLearningPlatform.demo.Lecture;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.naming.OperationNotSupportedException;

@RestController
@RequestMapping("/lecutre")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LectureController {
    private final LectureService lectureService;
    @PostMapping("/AddLecture/{courseId}")
    public ResponseEntity<?> AddLecture(@PathVariable("courseId") int courseId, @ModelAttribute @Valid LectureRequest request, Authentication connectedUser) throws Exception {
        return ResponseEntity.ok(lectureService.addLecture(request,courseId,connectedUser));
    }
    @GetMapping("/getoneLecture/{lectureId}")
    public ResponseEntity<?> GetOneLecture(@PathVariable("lectureId") int lectureId, Authentication connectedUser) {
        return ResponseEntity.ok(lectureService.findOneLecture(lectureId,connectedUser));
    }
    @DeleteMapping("/deletelecture/{lectureId}")    public ResponseEntity<?> DeleteLecture(@PathVariable("lectureId") int lectureId, Authentication connectedUser) throws Exception {
        return ResponseEntity.ok(lectureService.deleteoneLecture(lectureId,connectedUser));
    }
    @PatchMapping("/updatelecture/{lectureId}/{courseId}")
    public ResponseEntity<?> UpdateLecture(@PathVariable("lectureId") int lectureId,@PathVariable("courseId") int courseId, @RequestParam @Valid LectureRequest request, Authentication connectedUser) throws Exception {
        return ResponseEntity.ok(lectureService.updateoneLecture(request,lectureId,connectedUser,courseId));
    }
    @GetMapping("/getAllLecture/{courseId}")
    public ResponseEntity<?> GetallLecture(@PathVariable("courseId") int courseId,@RequestParam(value = "page",defaultValue = "0",required = false) int page,@RequestParam(value = "size",defaultValue = "5",required = false) int size, Authentication connectedUser){
        return ResponseEntity.ok(lectureService.getLectureofAllCourse(courseId,connectedUser,page,size));
    }

}
