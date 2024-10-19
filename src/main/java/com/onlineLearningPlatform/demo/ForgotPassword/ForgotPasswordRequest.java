package com.onlineLearningPlatform.demo.ForgotPassword;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ForgotPasswordRequest {
    @NotBlank(message = "email is mandatory")
    @NotEmpty(message = "email is mandatory")
    private String email;
}
