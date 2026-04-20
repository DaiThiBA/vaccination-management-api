package com.finalproject.vaccine_management.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BatchResponse {
    private String id;
    private String vaccineId;
    private String vaccineName;
    private String batchNumber;
    private LocalDate manufactureDate;
    private LocalDate expiryDate;
    private Integer quantity;
}