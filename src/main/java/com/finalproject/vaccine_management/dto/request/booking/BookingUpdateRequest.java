package com.finalproject.vaccine_management.dto.request.booking;

import com.finalproject.vaccine_management.dto.request.medicalRecord.MedicalRecordCreationRequest;
import com.finalproject.vaccine_management.entity.BookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingUpdateRequest {
    @NotNull
    private BookingStatus status;

    private MedicalRecordCreationRequest medicalRecordCreationRequest;
}
