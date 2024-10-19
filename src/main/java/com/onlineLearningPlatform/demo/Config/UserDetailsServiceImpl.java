package com.onlineLearningPlatform.demo.Config;

import com.onlineLearningPlatform.demo.User.UserRepositiory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private  final UserRepositiory userRepositiory;
    @Override
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
        var user= userRepositiory.GetUserbyEmail(useremail).orElseThrow(()->new UsernameNotFoundException("user does not exist"));
        //System.out.println(user);
        return user;
    }
}
