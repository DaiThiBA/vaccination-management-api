package com.finalproject.vaccine_management.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MedicalResponse {
    String id;
    String bookingId;
    String patientId;
    String patientName;

    String vaccineId;
    String vaccineName;

    LocalDateTime injectionDate;
    String reaction;

    String batchId;
    String batchNumber;

    String staffId;
    String staffName;

}
