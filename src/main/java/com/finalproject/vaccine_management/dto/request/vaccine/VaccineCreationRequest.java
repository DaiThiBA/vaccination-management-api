package com.finalproject.vaccine_management.dto.request.vaccine;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VaccineCreationRequest {
    @NotBlank
    private String vaccineName;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotBlank
    private String supplierId;
}
