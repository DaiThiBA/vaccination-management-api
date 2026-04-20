package com.finalproject.vaccine_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class VaccineResponse {
    private String id;
    private String vaccineName;
    private String description;
    private BigDecimal price;
    private String supplierId;
    private String supplierName;
}
