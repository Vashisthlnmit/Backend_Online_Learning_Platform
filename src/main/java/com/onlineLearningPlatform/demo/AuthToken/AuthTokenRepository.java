package com.onlineLearningPlatform.demo.AuthToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthToken,Integer> {
    @Query("""
       select t from AuthToken t inner join User u on t.user.id=u.id where u.id=:userId and (t.Expired=false or t.Revoked=false)
""")
    List<AuthToken> findAllValidTokenByUser(Integer userId);
    @Query("""
       select  t from AuthToken t where t.token=:token
""")
    Optional<AuthToken> findByToken(String token);
}
