package com.onlineLearningPlatform.demo.auth;

import com.onlineLearningPlatform.demo.AuthToken.AuthToken;
import com.onlineLearningPlatform.demo.AuthToken.AuthTokenRepository;
import com.onlineLearningPlatform.demo.AuthToken.TokenType;
import com.onlineLearningPlatform.demo.Config.JwtService;
import com.onlineLearningPlatform.demo.Role.Role;
import com.onlineLearningPlatform.demo.Role.RoleEnum;
import com.onlineLearningPlatform.demo.Role.RoleRepository;
import com.onlineLearningPlatform.demo.Token.Token;
import com.onlineLearningPlatform.demo.Token.TokenRepository;
import com.onlineLearningPlatform.demo.User.User;
import com.onlineLearningPlatform.demo.User.UserRepositiory;
import com.onlineLearningPlatform.demo.email.EmailSender;
import com.onlineLearningPlatform.demo.email.EmailTemplateName;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Authenticationservice {
    private  final UserRepositiory userrepo;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthTokenRepository authTokenRepository;
    private final JwtService jwtService;
    private String activationUrl;
    public void register(RegisterRequest request) throws MessagingException {
        Role role=null;
        String email=request.getEmail();
        if(email.endsWith("@lnmiitadmin.ac.in")){
            role=roleRepository.findByName("ADMIN").orElseThrow(
                    ()->new IllegalStateException("Role does not exist")
            );
        }
        else {
            role=roleRepository.findByName("USER").orElseThrow(
                    ()->new IllegalStateException("Role does not exist")
            );
        };
        var user=User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(role))
                .build();
        userrepo.save(user);
        sendValidationemail(user);
    }

    private String sendValidationemail(User user) throws MessagingException {
        var newtoken=generateandSaveActivationToken(user);
        emailSender.sendEmail(
                user.getEmail(),
                user.getUsername(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                newtoken,
                "Account Verification"
        );
        return newtoken;
    }

    private String  generateandSaveActivationToken(User user) {
        Integer generatedCode=(int) (100000*Math.random());
        String stringformattedcode=Integer.toString(generatedCode);
        var token= Token.builder()
                .code(stringformattedcode)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusHours(1))
                .user(user)
                .build();
        tokenRepository.save(token);
        return stringformattedcode;
    }

    public String verify(VerificationRequest request) throws Exception {
        User user=userrepo.GetUserbyEmail(request.getEmail()).orElseThrow(
                ()->new IllegalStateException("User does not exist")
        );
        Token token=tokenRepository.checkcode(request.getCode(),user.getEmail()).orElseThrow(
                ()->new IllegalStateException("Token does not exist")
        );
        if(LocalDateTime.now().isAfter(token.getExpiresAt())){
            tokenRepository.deleteById(token.getId());
            sendValidationemail(user);
            throw  new RuntimeException("token is expired we have send new token for verification");
        }
        else{
            user.setVerified(true);
            userrepo.save(user);
            token.setValidatedAt(LocalDateTime.now());
            tokenRepository.save(token);
            return "user is successfully verified";
        }
    }
    private void RevokeAllUserTokens(User user){
    }

    public AuthenticateResponse authenticate(AuthenticateRequest request) {
        System.out.println(request.getEmail());
        System.out.println(request.getPassword());
        var auth=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
        var claims=new HashMap<String,Object>();
        var user=((User)auth.getPrincipal());
        System.out.println(user.getEmail());
        var jwtToken=jwtService.generateToken(claims,user);
        revokeAllUserTokens(user);
        var authtoken= AuthToken.builder()
                .token(jwtToken)
                .user(user)
                .tokenType(TokenType.BEARER)
                .Expired(false)
                .Revoked(false)
                .build();
        authTokenRepository.save(authtoken);
        return AuthenticateResponse.builder().token(jwtToken).build();
    }
    public void revokeAllUserTokens(User user){
        var validUserTokens=authTokenRepository.findAllValidTokenByUser(user.getId());
        if(validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(t->{
            t.setRevoked(true);
            t.setExpired(true);
        });
        authTokenRepository.saveAll(validUserTokens);
    }
}
