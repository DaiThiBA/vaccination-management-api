package com.finalproject.vaccine_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
//@SQLRestriction("is_deleted = false") // xoá mềm
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "username", nullable = false,unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name= "is_deleted")
    private Boolean isDeleted = false;

    //user-role (n-1)
    //1 - 1 role
    //n user  - 1 role
    //    @ManyToOne
    //    @JoinColumn(name = "role_id")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;
}
