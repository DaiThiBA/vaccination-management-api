package com.finalproject.vaccine_management.dto.request.vacccinationSchedule;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleFilterRequest {
    private String vaccineId;
    private LocalDate date;
    private String location;
    private Boolean availableOnly;
}
