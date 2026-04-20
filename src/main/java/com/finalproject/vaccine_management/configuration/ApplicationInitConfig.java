package com.finalproject.vaccine_management.configuration;

import com.finalproject.vaccine_management.entity.RoleName;
import com.finalproject.vaccine_management.entity.User;
import com.finalproject.vaccine_management.repository.IUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(IUserRepository userRepository) {
        return applicationRunner -> {
            if(!userRepository.existsByUsername("admin")){

                Set<String> roles = new HashSet<>();

                roles.add(RoleName.ADMIN.name());

                User user = User.builder()
                        .username("admin")
                        .fullName("")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("default admin has been created with password: admin, please change it");
            }
        };

    };
}
