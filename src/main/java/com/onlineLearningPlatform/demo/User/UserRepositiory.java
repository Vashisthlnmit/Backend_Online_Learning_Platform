package com.onlineLearningPlatform.demo.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepositiory extends JpaRepository<User,Integer>{
    @Query("""
        Select u from User u where  u.email=:email
""")
    Optional<User> GetUserbyEmail(String email);
}
