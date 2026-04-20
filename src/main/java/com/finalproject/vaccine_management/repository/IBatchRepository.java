package com.finalproject.vaccine_management.repository;

import com.finalproject.vaccine_management.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IBatchRepository extends
        JpaRepository<Batch, String>,
        JpaSpecificationExecutor<Batch> {
}
