package com.finalproject.vaccine_management.service;

import com.finalproject.vaccine_management.dto.request.supplier.SupplierCreationRequest;
import com.finalproject.vaccine_management.dto.request.supplier.SupplierFilterRequest;
import com.finalproject.vaccine_management.dto.request.supplier.SupplierUpdateRequest;
import com.finalproject.vaccine_management.dto.response.SupplierResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISupplierService {
    SupplierResponse create(SupplierCreationRequest request);

    Page<SupplierResponse> filter(SupplierFilterRequest filter, Pageable pageable);

    SupplierResponse update(SupplierUpdateRequest supplierUpdateRequest, String id);

    Boolean delete(String id);
}
