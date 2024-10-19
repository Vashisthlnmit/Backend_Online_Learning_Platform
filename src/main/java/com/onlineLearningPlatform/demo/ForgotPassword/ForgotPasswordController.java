package com.onlineLearningPlatform.demo.ForgotPassword;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/ForgotPassword")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ForgotPasswordController {
    private final ForgotPasswordService forgotPasswordService;
    @PostMapping("/requestforPassword")
    public ResponseEntity<?> requestForPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) throws MessagingException {
        return ResponseEntity.ok().body(forgotPasswordService.ChangePassword(forgotPasswordRequest.getEmail()));
    }
    @PostMapping("/verificationofPasswordChange")
    public ResponseEntity<?> requestForPasswordChange(@RequestBody ForgotPasswordRequest forgotPasswordRequest) throws MessagingException {
        return ResponseEntity.ok().body(forgotPasswordService.ChangePassword(forgotPasswordRequest.getEmail()));
    }
}
