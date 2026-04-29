package com.finalproject.vaccine_management.service.Impl;

import com.finalproject.vaccine_management.dto.request.user.UserCreationRequest;
import com.finalproject.vaccine_management.dto.request.user.UserFilterRequest;
import com.finalproject.vaccine_management.dto.request.user.UserUpdateRequest;
import com.finalproject.vaccine_management.dto.response.ApiResponse;
import com.finalproject.vaccine_management.dto.response.UserResponse;
import com.finalproject.vaccine_management.entity.RoleName;
import com.finalproject.vaccine_management.entity.User;
import com.finalproject.vaccine_management.exception.AppException;
import com.finalproject.vaccine_management.exception.ErrorCode;
import com.finalproject.vaccine_management.mapper.UserMapper;
import com.finalproject.vaccine_management.repository.IUserRepository;
import com.finalproject.vaccine_management.service.IUserService;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService implements IUserService {

    IUserRepository userRepository;
    UserMapper userMapper;

    public ApiResponse<User> createUser(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = userMapper.fromUserCreate(request);
        user.setPassword(encoder.encode(request.getPassword()));
        user.setIsDeleted(false);

        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch( r -> r.getAuthority().equals("ROLE_ADMIN"));

        Set<String> roles = (isAdmin && request.getRoles() != null && !request.getRoles().isEmpty() )
                ? request.getRoles()
                : Set.of(RoleName.USER.name());

        user.setRoles(roles);

        return ApiResponse.<User>builder()
                .code(1000)
                .result(userRepository.save(user))
                .build();
    }

    public Page<UserResponse> filter(UserFilterRequest request, Pageable pageable) {
        Specification<User> spec = buildFilter(request);

        Page<User> userPage = userRepository.findAll(spec, pageable);

        List<UserResponse> list = new ArrayList<>();

        for (User user : userPage.getContent()) {
            list.add(userMapper.toUserResponse(user));
        }

        return new PageImpl<>(list, pageable, userPage.getTotalElements());
    }

    public Specification<User> buildFilter(UserFilterRequest req) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // username
            if (req.getUsername() != null && !req.getUsername().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("username")),
                        "%" + req.getUsername().toLowerCase() + "%"
                ));
            }

            // fullName
            if (req.getFullName() != null && !req.getFullName().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("fullName")),
                        "%" + req.getFullName().toLowerCase() + "%"
                ));
            }

            // email
            if (req.getEmail() != null && !req.getEmail().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("email")),
                        "%" + req.getEmail().toLowerCase() + "%"
                ));
            }

            // phone
            if (req.getPhone() != null && !req.getPhone().isBlank()) {
                predicates.add(cb.like(
                        root.get("phone"),
                        "%" + req.getPhone() + "%"
                ));
            }

            // roles (Set<String>)
            if (req.getRoles() != null && !req.getRoles().isEmpty()) {

                List<Predicate> rolePredicates = new ArrayList<>();

                for (String role : req.getRoles()) {
                    rolePredicates.add(cb.isMember(role, root.get("roles")));
                }

                // OR: user có 1 trong các role
                predicates.add(cb.or(rolePredicates.toArray(new Predicate[0])));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    @Transactional
    public UserResponse update(UserUpdateRequest userUpdateRequest, String id) {
        User user = userRepository.findById(id)
                .orElseThrow( () -> new AppException(ErrorCode.USER_NOT_FOUND));

        User userUpdate = userMapper.fromUserUpdate(userUpdateRequest);

        userUpdate.setId(id);
        userUpdate.setUsername(user.getUsername());
        userUpdate.setIsDeleted(user.getIsDeleted());
        userUpdate.setPassword(user.getPassword());

        return userMapper.toUserResponse(userRepository.save(userUpdate));

        }

    @Override
    public UserResponse delete(String id) {
        User user = userRepository.findById(id)
                .orElseThrow( () -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);

        return userMapper.toUserResponse(user);
    }
}

    //    public Page<UserResponse> filter(UserFilterRequest request, Pageable pageable){
////        var authentication = SecurityContextHolder.getContext()
////                .getAuthentication();
////
////        log.info("Username: {}", authentication.getName());
////
////        authentication.getAuthorities().forEach(
////                grantedAuthority -> log.info(grantedAuthority.getAuthority()));
//        Specification<User> spec = buildFilter(request);
//
//
//        Page<UserResponse> result = new ArrayList<>();
//
//        for(User user : userRepository.findAll(spec,pageable)){
//            result.add(userMapper.toUserResponse(user));
//        }
//
//        return result;
//    }
