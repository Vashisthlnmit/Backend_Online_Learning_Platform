package com.onlineLearningPlatform.demo.ForgotPassword;

import com.onlineLearningPlatform.demo.User.User;
import com.onlineLearningPlatform.demo.User.UserRepositiory;
import com.onlineLearningPlatform.demo.email.EmailSender;
import com.onlineLearningPlatform.demo.email.EmailTemplateName;
import jakarta.mail.MessagingException;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@Builder
@Setter
@Getter
@RequiredArgsConstructor
public class ForgotPasswordService {
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final UserRepositiory userRepositiory;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;
    public  String RandomCodeGenerator() {
        Integer code=(int) (100000*Math.random());
        return Integer.toString(code);
    }
    public String ChangePassword(String email) throws MessagingException {
        User user=userRepositiory.GetUserbyEmail(email).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        String code=RandomCodeGenerator();
        ForgotPassword SaveCode=ForgotPassword.builder()
        .token(code)
                .expirationDate(LocalDateTime.now().plusHours(1))
                .isexpired(false)
                .user(user)
                .build();
        forgotPasswordRepository.save(SaveCode);
        emailSender.sendEmail(email,user.getUsername(), EmailTemplateName.PASSWORD_RESET,code,"This is verification code for your Email");
        return  code;
    }
    public String ResetPassword(ChangePasswordRequest changePasswordRequest){
      ForgotPassword gotuser=forgotPasswordRepository.findbyValidemailandCode(changePasswordRequest.getEmail(),changePasswordRequest.getVerificationCode()).orElseThrow(
                ()->new RuntimeException("Either token does not exist or Email does not exist")
        );
      if(LocalDateTime.now().isAfter(gotuser.getExpirationDate())){
          throw new RuntimeException("Token is expired");
      }
      User user=userRepositiory.GetUserbyEmail(changePasswordRequest.getEmail()).orElseThrow(
              ()->new RuntimeException("no user found")
      );
      user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
      gotuser.setIsexpired(true);
      forgotPasswordRepository.save(gotuser);
      userRepositiory.save(user);
        return "Password reset successfully";
    }
}
