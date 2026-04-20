package com.finalproject.vaccine_management.controller;


import com.finalproject.vaccine_management.dto.request.booking.BookingCreationRequest;
import com.finalproject.vaccine_management.dto.request.booking.BookingFilterRequest;
import com.finalproject.vaccine_management.dto.request.vaccine.VaccineCreationRequest;
import com.finalproject.vaccine_management.dto.request.vaccine.VaccineFilterRequest;
import com.finalproject.vaccine_management.dto.response.ApiResponse;
import com.finalproject.vaccine_management.dto.response.BookingResponse;
import com.finalproject.vaccine_management.dto.response.VaccineResponse;
import com.finalproject.vaccine_management.service.IVaccineService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vaccines")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VaccineController {
    IVaccineService vaccineService;

    @PostMapping()
    ApiResponse<VaccineResponse> create(@RequestBody @Valid VaccineCreationRequest request){
        return ApiResponse.<VaccineResponse>builder()
                .result(vaccineService.create(request))
                .build();
    }

    @GetMapping()
    ApiResponse<Page<VaccineResponse>> filter(
            @ModelAttribute VaccineFilterRequest filter,
            Pageable pageable
    ){
        return ApiResponse.<Page<VaccineResponse>>builder()
                .result(vaccineService.filter(filter, pageable))
                .build();
    }
}
