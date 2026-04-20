package com.finalproject.vaccine_management.dto.request.supplier;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierUpdateRequest {
    private String name;
    private String contactInfo;
    private String address;
}
