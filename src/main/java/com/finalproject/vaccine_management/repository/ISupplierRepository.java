package com.finalproject.vaccine_management.repository;

import com.finalproject.vaccine_management.entity.Supplier;
import com.finalproject.vaccine_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ISupplierRepository extends
        JpaRepository<Supplier, String>
        ,JpaSpecificationExecutor<Supplier> {
    boolean existsByName(String name);
}
