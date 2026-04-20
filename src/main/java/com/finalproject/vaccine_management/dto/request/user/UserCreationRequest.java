package com.finalproject.vaccine_management.dto.request.user;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserCreationRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String fullName;
    @NotNull
    private String email;
    @NotNull
    private String phone;
    @NotNull
    private Set<String> roles;
}

