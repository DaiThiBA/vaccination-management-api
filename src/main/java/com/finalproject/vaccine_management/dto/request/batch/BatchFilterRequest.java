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
public class BatchFilterRequest {
    private String keyword;
    private String vaccineId;

    private Integer minQuantity;
    private Integer maxQuantity;

    private LocalDate expiryFrom;
    private LocalDate expiryTo;
}
