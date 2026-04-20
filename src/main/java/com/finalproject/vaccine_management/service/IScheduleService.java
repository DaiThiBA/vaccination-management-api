package com.finalproject.vaccine_management.service;

import com.finalproject.vaccine_management.dto.request.vacccinationSchedule.ScheduleCreationRequest;
import com.finalproject.vaccine_management.dto.request.vacccinationSchedule.ScheduleFilterRequest;
import com.finalproject.vaccine_management.dto.request.vacccinationSchedule.ScheduleUpdateRequest;
import com.finalproject.vaccine_management.dto.response.VaccineScheduleResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IScheduleService {
    VaccineScheduleResponse create(@Valid ScheduleCreationRequest request);

    Page<VaccineScheduleResponse> filter(ScheduleFilterRequest filter, Pageable pageable);

    VaccineScheduleResponse getById(String id);

    VaccineScheduleResponse update(ScheduleUpdateRequest scheduleUpdateRequest, String id);
}
