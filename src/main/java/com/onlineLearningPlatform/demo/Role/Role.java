package com.onlineLearningPlatform.demo.Role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onlineLearningPlatform.demo.User.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Role {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime modifiedDate;
//    @ManyToMany(mappedBy = "roles")
//    @JsonIgnore
//    private List<User> users;
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> user;


}
