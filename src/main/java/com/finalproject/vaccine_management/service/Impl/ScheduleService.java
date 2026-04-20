package com.finalproject.vaccine_management.service.Impl;

import com.finalproject.vaccine_management.dto.request.vacccinationSchedule.ScheduleCreationRequest;
import com.finalproject.vaccine_management.dto.request.vacccinationSchedule.ScheduleFilterRequest;
import com.finalproject.vaccine_management.dto.request.vacccinationSchedule.ScheduleUpdateRequest;
import com.finalproject.vaccine_management.dto.response.VaccineScheduleResponse;
import com.finalproject.vaccine_management.entity.VaccinationSchedule;
import com.finalproject.vaccine_management.entity.Vaccine;
import com.finalproject.vaccine_management.exception.AppException;
import com.finalproject.vaccine_management.exception.ErrorCode;
import com.finalproject.vaccine_management.mapper.ScheduleMapper;
import com.finalproject.vaccine_management.repository.IScheduleRepository;
import com.finalproject.vaccine_management.repository.IVaccineRepository;
import com.finalproject.vaccine_management.service.IScheduleService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService implements IScheduleService {

    private final IScheduleRepository scheduleRepository;
    private final IVaccineRepository vaccineRepository;
    private final ScheduleMapper mapper;

    @Override
    public VaccineScheduleResponse create(ScheduleCreationRequest request) {

        Vaccine vaccine = vaccineRepository.findById(request.getVaccineId())
                .orElseThrow(() -> new AppException(ErrorCode.VACCINE_NOT_FOUND));

        VaccinationSchedule schedule = mapper.fromCreate(request);
        schedule.setVaccine(vaccine);
        schedule.setBookedCount(0);

        return mapper.toResponse(scheduleRepository.save(schedule));
    }

    @Override
    @Transactional(readOnly = true)// giữa session sống từ đầu đến cuối
    public Page<VaccineScheduleResponse> filter(ScheduleFilterRequest filter, Pageable pageable) {

        Specification<VaccinationSchedule> spec = buildFilter(filter);

        Page<VaccinationSchedule> vaccinationSchedulePage = scheduleRepository.findAll(spec, pageable);

        List<VaccineScheduleResponse> list = new ArrayList<>();

        for(VaccinationSchedule vaccinationSchedule : vaccinationSchedulePage.getContent()){
            list.add(mapper.toResponse(vaccinationSchedule));
        }

        return new PageImpl<>(list, pageable, vaccinationSchedulePage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public VaccineScheduleResponse getById(String id) {

        VaccinationSchedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        return mapper.toResponse(schedule);
    }

    @Override
    @Transactional
    public VaccineScheduleResponse update(ScheduleUpdateRequest request, String id) {

        VaccinationSchedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        mapper.updateSchedule(schedule,request);

        return mapper.toResponse(scheduleRepository.save(schedule));
    }

    private Specification<VaccinationSchedule> buildFilter(ScheduleFilterRequest filter) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getVaccineId() != null) {
                predicates.add(cb.equal(root.get("vaccine").get("id"), filter.getVaccineId()));
            }

            if (filter.getDate() != null) {
                predicates.add(cb.equal(root.get("scheduleDate"), filter.getDate()));
            }

            if (filter.getLocation() != null && !filter.getLocation().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("location")),
                        "%" + filter.getLocation().toLowerCase() + "%"
                ));
            }

            if (Boolean.TRUE.equals(filter.getAvailableOnly())) {
                predicates.add(cb.lessThan(root.get("bookedCount"), root.get("capacity")));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}