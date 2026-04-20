package com.finalproject.vaccine_management.controller;

import com.finalproject.vaccine_management.dto.request.vacccinationSchedule.ScheduleCreationRequest;
import com.finalproject.vaccine_management.dto.request.vacccinationSchedule.ScheduleFilterRequest;
import com.finalproject.vaccine_management.dto.request.vacccinationSchedule.ScheduleUpdateRequest;
import com.finalproject.vaccine_management.dto.response.ApiResponse;
import com.finalproject.vaccine_management.dto.response.VaccineScheduleResponse;
import com.finalproject.vaccine_management.service.IScheduleService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleController {

    IScheduleService scheduleService;

    @PostMapping()
    ApiResponse<VaccineScheduleResponse> create(@Valid @RequestBody ScheduleCreationRequest request){
        return ApiResponse.<VaccineScheduleResponse>builder()
                .result(scheduleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<Page<VaccineScheduleResponse>> filter(
            @ModelAttribute ScheduleFilterRequest filter,
            Pageable pageable
    ){
        return ApiResponse.<Page<VaccineScheduleResponse>>builder()
                .result(scheduleService.filter(filter,pageable))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<VaccineScheduleResponse> getById(
            @PathVariable String id
    ){
        return ApiResponse.<VaccineScheduleResponse>builder()
                .result(scheduleService.getById(id))
                .build();
    }


    @PutMapping("/{id}")
    ApiResponse<VaccineScheduleResponse> update(
            @RequestBody ScheduleUpdateRequest scheduleUpdateRequest,
            @PathVariable String id){
        return ApiResponse.<VaccineScheduleResponse>builder()
                .result(scheduleService.update(scheduleUpdateRequest, id))
                .build();
    }







}
