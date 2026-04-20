package com.finalproject.vaccine_management.dto.request.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingCreationRequest {
    private String userId;
    private LocalDateTime appointmentDate;
    private String scheduleId;
    private String fullName;
    private LocalDate dob;
    private String phone;
    private String guardianName;
    private String note;

}
