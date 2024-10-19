package com.onlineLearningPlatform.demo.OnlineQuiz;
import com.onlineLearningPlatform.demo.Common.PageResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
@CrossOrigin("*")
@AllArgsConstructor
public class QuizController {
    private final  QuizService quizService;
    @PostMapping("/create/{Courseid}")
    public ResponseEntity<?> createQuiz(@RequestBody @Valid QuizRequest quizrequest, @PathVariable int Courseid, Authentication connectUser) {
        return ResponseEntity.ok(quizService.CreateQuiz(quizrequest,Courseid,connectUser));
    }
    @DeleteMapping("/delete/{quizid}")
    public ResponseEntity<?> deleteQuiz(@PathVariable int quizid, Authentication connectUser) {
        return ResponseEntity.ok(quizService.DeleteQuiz(quizid,connectUser));

    }
    @PatchMapping("/updatequiz/{quizid}")
    public ResponseEntity<?> updateQuiz(@PathVariable  QuizRequest quizrequest, @PathVariable int quizid, Authentication connectUser) {
        return ResponseEntity.ok(quizService.UpdateQuiz(quizrequest,quizid,connectUser));
    }
    @GetMapping("/getallquiz/{Courseid}")
    public ResponseEntity<PageResponse<?>> getAllQuiz(@PathVariable int Courseid, Authentication connectUser, @RequestParam(name = "page",defaultValue = "0",required = false) int page, @RequestParam(name = "size",defaultValue = "5",required = false)int size) {
        return ResponseEntity.ok(quizService.GetallQuiz(Courseid,page,size));
    }
}
