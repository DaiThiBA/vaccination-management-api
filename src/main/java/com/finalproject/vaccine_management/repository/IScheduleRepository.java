package com.finalproject.vaccine_management.repository;

import com.finalproject.vaccine_management.entity.Supplier;
import com.finalproject.vaccine_management.entity.VaccinationSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IScheduleRepository extends
        JpaRepository<VaccinationSchedule, String>,
        JpaSpecificationExecutor<VaccinationSchedule> {

    // Fetch vaccine cùng schedule để tránh LazyInitializationException
    // và N+1 query (nếu không, mỗi record sẽ trigger thêm query khi access vaccine)
    @EntityGraph(attributePaths = "vaccine")
    Page<VaccinationSchedule> findAll(Specification<VaccinationSchedule> spec, Pageable pageable);

}
