package com.finalproject.vaccine_management.repository;

import com.finalproject.vaccine_management.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IVaccineRepository extends
        JpaRepository<Vaccine, String>,
        JpaSpecificationExecutor<Vaccine> {
}
