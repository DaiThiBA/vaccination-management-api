package com.finalproject.vaccine_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BookingResponse {

    private String id;

    private String userId;
    private String userName;

    private String fullName;
    private LocalDate dob;
    private String phone;
    private String guardianName;

    private String scheduleId;
    private LocalDate scheduleDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;

    private String vaccineName;

    private String status;
    private String note;
}