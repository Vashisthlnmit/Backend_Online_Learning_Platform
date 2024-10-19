package com.onlineLearningPlatform.demo.Config;

import com.onlineLearningPlatform.demo.AuthToken.AuthTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private  final  JwtService jwtService;
    private  final UserDetailsService userDetailsService;
    private  final AuthTokenRepository authTokenRepository;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
       System.out.println(request.getServletPath());
        if(request.getServletPath().matches("/auth/signup")){
            System.out.println("subcheck1");
            filterChain.doFilter(request,response);
            return;
        }
        if(request.getServletPath().matches("/Course/getAllcourse")){
            System.out.println("subcheck2");
            filterChain.doFilter(request,response);
            return;
        }
        final String authHeader = request.getHeader(AUTHORIZATION);
        //System.out.println(authHeader);
        final String jwt;
        final  String useremail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt=authHeader.substring(7);
        useremail=jwtService.extractUsername(jwt);
        if(useremail!=null && SecurityContextHolder.getContext().getAuthentication()==null){
           UserDetails user =userDetailsService.loadUserByUsername(useremail);
           var isTokenvalid=authTokenRepository.findByToken(jwt).map(t-> !t.isExpired() && !t.isRevoked()).orElse(false);

           if(jwtService.isTokenvalid(jwt,user) && isTokenvalid){
              // System.out.println("token is valid");
               UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(
                       user, null, user.getAuthorities()
               );
               authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authtoken);
               //System.out.println("this is done");
           }
        }
        filterChain.doFilter(request,response);
    }
}
