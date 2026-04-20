package com.finalproject.vaccine_management.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SupplierResponse {
    private String id;
    private String name;
    private String address;
    private String contactInfo;
}
