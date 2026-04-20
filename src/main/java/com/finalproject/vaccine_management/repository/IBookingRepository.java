package com.finalproject.vaccine_management.repository;

import com.finalproject.vaccine_management.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface IBookingRepository extends
        JpaRepository<Booking, String>,
        JpaSpecificationExecutor<Booking> {

    @EntityGraph(attributePaths = {"user", "schedule", "schedule.vaccine"})
    Optional<Booking> findById(String id);

    @EntityGraph(attributePaths = {"user", "schedule", "schedule.vaccine"})
    Page<Booking> findAll(Specification<Booking> spec, Pageable pageable);
}
