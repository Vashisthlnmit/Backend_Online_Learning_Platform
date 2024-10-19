package com.onlineLearningPlatform.demo.Token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("""
      Select t from Token t where t.code=:code and t.user.email=:email
""")
    Optional<Token> checkcode(String code,String email);
}
