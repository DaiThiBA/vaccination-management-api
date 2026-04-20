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
public class SupplierCreationRequest {
    @NotNull
    private String name;
    @NotNull
    private String contactInfo;
    @NotNull
    private String address;
}
