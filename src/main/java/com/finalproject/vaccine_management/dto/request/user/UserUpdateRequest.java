package com.finalproject.vaccine_management.dto.request.user;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    @NotNull
    private String fullName;
    @NotNull
    private String email;
    @NotNull
    private String phone;
    @NotNull
    private Set<String> roles;
}

