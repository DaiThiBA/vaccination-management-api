package com.finalproject.vaccine_management.mapper;

import com.finalproject.vaccine_management.dto.request.vaccine.VaccineCreationRequest;
import com.finalproject.vaccine_management.dto.response.VaccineResponse;
import com.finalproject.vaccine_management.entity.Vaccine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VaccineMapper {
    Vaccine fromVaccineCreation(VaccineCreationRequest request);

    @Mapping(target = "supplierId", source = "supplier.id")
    @Mapping(target = "supplierName", source = "supplier.name")
    VaccineResponse toVaccineResponse(Vaccine vaccine);
}
