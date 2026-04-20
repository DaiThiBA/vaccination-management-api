package com.finalproject.vaccine_management.controller;

import com.finalproject.vaccine_management.dto.request.user.UserCreationRequest;
import com.finalproject.vaccine_management.dto.request.user.UserFilterRequest;
import com.finalproject.vaccine_management.dto.request.user.UserUpdateRequest;
import com.finalproject.vaccine_management.dto.response.ApiResponse;
import com.finalproject.vaccine_management.dto.response.UserResponse;
import com.finalproject.vaccine_management.entity.User;
import com.finalproject.vaccine_management.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    private IUserService userService;

    @PostMapping()
    ApiResponse<User> createUser(@RequestBody UserCreationRequest request){
        return userService.createUser(request);
    }

    @GetMapping()
    ApiResponse<Page<UserResponse>> filter(
            @ModelAttribute UserFilterRequest request,
            Pageable pageable
    ){
        return ApiResponse.<Page<UserResponse>>builder()
                .result(userService.filter(request, pageable))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<UserResponse> update(
            @RequestBody UserUpdateRequest userUpdateRequest,
            @PathVariable String id){
        return ApiResponse.<UserResponse>builder()
                .result(userService.update(userUpdateRequest, id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<UserResponse> delete(@PathVariable String id){
        return ApiResponse.<UserResponse>builder()
                .result(userService.delete(id))
                .build();
    }

}