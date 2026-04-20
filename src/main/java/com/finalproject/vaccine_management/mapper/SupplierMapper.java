package com.finalproject.vaccine_management.mapper;


import com.finalproject.vaccine_management.dto.request.supplier.SupplierCreationRequest;
import com.finalproject.vaccine_management.dto.request.supplier.SupplierUpdateRequest;
import com.finalproject.vaccine_management.dto.response.SupplierResponse;
import com.finalproject.vaccine_management.entity.Supplier;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier fromCreate(SupplierCreationRequest request);
    SupplierResponse toSupplierResponse(Supplier supplier);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSupplier(@MappingTarget Supplier supplier, SupplierUpdateRequest request);
}
