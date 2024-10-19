package com.onlineLearningPlatform.demo.ForgotPassword;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {
    @Query("""
       select t from ForgotPassword t where t.user.email=:email and t.token=:code  and t.isexpired=false 
""")
    Optional<ForgotPassword> findbyValidemailandCode(String email, String code);
}
