package com.finalproject.vaccine_management.dto.request.batch;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatchCreationRequest {
    @NotNull(message = "Vaccine ID is required")
    private String vaccineId;

    @NotBlank(message = "Batch number is required")
    private String batchNumber;

    @NotNull(message = "Manufacture date is required")
    private LocalDate manufactureDate;

    @NotNull(message = "Expiry date is required")
    private LocalDate expiryDate;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;
}
