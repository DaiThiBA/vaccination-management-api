package com.finalproject.vaccine_management.repository;

import com.finalproject.vaccine_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends
        JpaRepository<User,String>,
        JpaSpecificationExecutor<User> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
