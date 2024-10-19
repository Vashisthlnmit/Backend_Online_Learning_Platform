package com.onlineLearningPlatform.demo.auth;


import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173/")
@RequiredArgsConstructor
public class AuthenticationController {
    private final Authenticationservice service;
    @PostMapping(value = "/signup",consumes = "application/json")
    //TODO - response Entity creation
    public ResponseEntity<?> Register(@RequestBody @Valid RegisterRequest request) throws MessagingException {
        System.out.println(request);
        service.register(request);
        return ResponseEntity.ok("user registered successfully email has been sent");
    }
    @PostMapping("/verify")
    public ResponseEntity<String> Verification(@RequestBody @Valid VerificationRequest request) throws Exception {
        System.out.println(request);
        return  ResponseEntity.ok(service.verify(request));
    }
    @PostMapping("/signin")
    public ResponseEntity<AuthenticateResponse> Signin(@RequestBody @Valid AuthenticateRequest request){
        //System.out.println(request);
        return ResponseEntity.ok(service.authenticate(request));
    }

}
