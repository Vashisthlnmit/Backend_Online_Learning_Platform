package com.onlineLearningPlatform.demo.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VerificationRequest {
    @NotBlank(message = "email is mandatory")
    @NotEmpty(message = "email is mandatory")
    @Email(message = "email is not properly formatted")
    private String email;
    @NotBlank(message = "code is mandatory for verification")
    @NotEmpty(message = "code is mandatory for verification")
    private String code;
}
