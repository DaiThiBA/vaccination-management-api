package com.finalproject.vaccine_management.dto.request.vaccine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VaccineFilterRequest {
    private String vaccineName;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String supplierId;
}
