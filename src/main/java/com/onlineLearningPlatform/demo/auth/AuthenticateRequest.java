package com.onlineLearningPlatform.demo.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticateRequest {
    @NotBlank(message = "email is mandatory")
    @NotEmpty(message = "email is mandatory")
    private String email;
    @NotBlank(message = "password is mandatory")
    @NotEmpty(message = "password is mandatory")
    private String password;
}
