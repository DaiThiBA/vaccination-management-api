package com.finalproject.vaccine_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class VaccineScheduleResponse {
    private String id;

    private String vaccineId;
    private String vaccineName;

    private LocalDate scheduleDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private Integer capacity;
    private Integer bookedCount;

    private String location;
}
