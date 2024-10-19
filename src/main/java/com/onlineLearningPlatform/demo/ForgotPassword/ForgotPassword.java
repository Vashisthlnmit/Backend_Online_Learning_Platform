package com.onlineLearningPlatform.demo.ForgotPassword;

import com.onlineLearningPlatform.demo.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class ForgotPassword {
    @Id
    @GeneratedValue
    Integer id;
    String token;
    private LocalDateTime expirationDate;
    private boolean isexpired;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
