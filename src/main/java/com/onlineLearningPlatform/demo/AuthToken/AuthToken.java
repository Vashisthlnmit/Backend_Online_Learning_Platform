package com.onlineLearningPlatform.demo.AuthToken;

import com.onlineLearningPlatform.demo.User.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class AuthToken {
    @Id
    @GeneratedValue
    private  Integer id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private boolean Expired;
    private  boolean Revoked;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
