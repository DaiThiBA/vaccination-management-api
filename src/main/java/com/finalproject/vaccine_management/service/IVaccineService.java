package com.finalproject.vaccine_management.service;

import com.finalproject.vaccine_management.dto.request.vaccine.VaccineCreationRequest;
import com.finalproject.vaccine_management.dto.request.vaccine.VaccineFilterRequest;
import com.finalproject.vaccine_management.dto.response.VaccineResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IVaccineService {
    VaccineResponse create(@Valid VaccineCreationRequest request);

    Page<VaccineResponse> filter(VaccineFilterRequest filter, Pageable pageable);
}
