package com.finalproject.vaccine_management.repository;

import com.finalproject.vaccine_management.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IMedicalRepository extends
        JpaRepository<MedicalRecord, String>
        , JpaSpecificationExecutor<MedicalRecord> {
}
