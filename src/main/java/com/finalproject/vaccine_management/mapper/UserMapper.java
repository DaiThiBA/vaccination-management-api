package com.finalproject.vaccine_management.mapper;

import com.finalproject.vaccine_management.dto.request.user.UserCreationRequest;
import com.finalproject.vaccine_management.dto.request.user.UserUpdateRequest;
import com.finalproject.vaccine_management.dto.response.UserResponse;
import com.finalproject.vaccine_management.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromUserCreate(UserCreationRequest request);

    User fromUserUpdate(UserUpdateRequest request);

    UserResponse toUserResponse(User user);
}
