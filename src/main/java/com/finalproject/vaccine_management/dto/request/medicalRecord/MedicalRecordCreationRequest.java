package com.finalproject.vaccine_management.dto.request.medicalRecord;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalRecordCreationRequest {
    @NotBlank
    private String bookingId;

    @NotBlank
    private String batchId;

    @NotBlank
    private String staffId;

    @NotNull
    private LocalDateTime injectionDate;
    private String reaction;
}
