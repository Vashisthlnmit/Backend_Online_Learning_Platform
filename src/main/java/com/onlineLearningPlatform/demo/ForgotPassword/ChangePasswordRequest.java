package com.onlineLearningPlatform.demo.ForgotPassword;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ChangePasswordRequest {
    private String email;
    private String verificationCode;
    private String newPassword;
}
