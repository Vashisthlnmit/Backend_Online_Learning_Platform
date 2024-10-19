package com.onlineLearningPlatform.demo.auth;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
public class RegisterRequest {
    @NotBlank(message = "username is mandatory")
    @NotEmpty(message = "username is mandatory")
    private String username;
    @NotBlank(message = "email is mandatory")
    @NotEmpty(message = "email is mandatory")
    @Email(message = "email is not properly formatted")
    private String email;
    @NotBlank(message = "password is mandatory")
    @NotEmpty(message = "password is mandatory")
    @Size(min = 8,message = "minimum 8 length password is required")
    private String password;
}
