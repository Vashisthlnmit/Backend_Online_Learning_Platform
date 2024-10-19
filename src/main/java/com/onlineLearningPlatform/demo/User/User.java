package com.onlineLearningPlatform.demo.User;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.onlineLearningPlatform.demo.AuthToken.AuthToken;
import com.onlineLearningPlatform.demo.Course.Course;
import com.onlineLearningPlatform.demo.Role.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Array;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_User")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails, Principal {
    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean isVerified;
//    @ManyToMany(fetch = FetchType.EAGER)
//    private List<Role> roles;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    @OneToMany(mappedBy = "owner")
    private List<Course> courses;
    @Override
    public String getName() {
        return email;
    }
    @ManyToMany
    private List<Course> registeredCourses;
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    List<AuthToken> authTokens;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return roles.stream()
               .map(role->new SimpleGrantedAuthority(role.getName()))
               .collect(Collectors.toList());
//        SimpleGrantedAuthority authority=new SimpleGrantedAuthority(role.getName().toString());
//        return List.of(authority);

    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
