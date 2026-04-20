package com.finalproject.vaccine_management.dto.request.batch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatchUpdateRequest {
    private String batchNumber;
    private LocalDate manufactureDate;
    private LocalDate expiryDate;
}
