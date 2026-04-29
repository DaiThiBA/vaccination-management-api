package com.finalproject.vaccine_management.controller;

import com.finalproject.vaccine_management.dto.request.medicalRecord.MedicalRecordCreationRequest;
import com.finalproject.vaccine_management.dto.response.ApiResponse;
import com.finalproject.vaccine_management.dto.response.MedicalResponse;
import com.finalproject.vaccine_management.service.IMedicalRecordService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MedicalRecordController {

    IMedicalRecordService medicalRecordService;

    @PostMapping()
    ApiResponse<MedicalResponse> create(@Valid @RequestBody MedicalRecordCreationRequest request){
        return ApiResponse.<MedicalResponse>builder()
                .result(medicalRecordService.create(request))
                .build();
    }


}
