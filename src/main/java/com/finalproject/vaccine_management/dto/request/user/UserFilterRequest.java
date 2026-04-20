package com.finalproject.vaccine_management.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFilterRequest {
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private Set<String> roles;
}


