package com.finalproject.vaccine_management.service;

import com.finalproject.vaccine_management.dto.request.user.UserCreationRequest;
import com.finalproject.vaccine_management.dto.request.user.UserFilterRequest;
import com.finalproject.vaccine_management.dto.request.user.UserUpdateRequest;
import com.finalproject.vaccine_management.dto.response.ApiResponse;
import com.finalproject.vaccine_management.dto.response.UserResponse;
import com.finalproject.vaccine_management.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    ApiResponse<User> createUser(UserCreationRequest request);
    Page<UserResponse> filter(UserFilterRequest request, Pageable pageable) ;

    UserResponse update(UserUpdateRequest userUpdateRequest, String id);

    UserResponse delete(String id);
}
