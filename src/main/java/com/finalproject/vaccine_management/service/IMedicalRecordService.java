package com.finalproject.vaccine_management.service;

import com.finalproject.vaccine_management.dto.request.medicalRecord.MedicalRecordCreationRequest;
import com.finalproject.vaccine_management.dto.response.MedicalResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

public interface IMedicalRecordService {
    MedicalResponse create(@Valid MedicalRecordCreationRequest request);
}
