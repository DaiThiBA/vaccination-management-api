package com.finalproject.vaccine_management.controller;


import com.finalproject.vaccine_management.dto.request.supplier.SupplierCreationRequest;
import com.finalproject.vaccine_management.dto.request.supplier.SupplierFilterRequest;
import com.finalproject.vaccine_management.dto.request.supplier.SupplierUpdateRequest;
import com.finalproject.vaccine_management.dto.request.user.UserUpdateRequest;
import com.finalproject.vaccine_management.dto.response.ApiResponse;
import com.finalproject.vaccine_management.dto.response.SupplierResponse;
import com.finalproject.vaccine_management.dto.response.UserResponse;
import com.finalproject.vaccine_management.service.ISupplierService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SupplierController {
    ISupplierService supplierService;

    @PostMapping()
    ApiResponse<SupplierResponse> create(@Valid @RequestBody SupplierCreationRequest request){
        return ApiResponse.<SupplierResponse>builder()
                .result(supplierService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<Page<SupplierResponse>> filter(
            @ModelAttribute SupplierFilterRequest filter,
            Pageable pageable
            ){
        return ApiResponse.<Page<SupplierResponse>>builder()
                .result(supplierService.filter(filter,pageable))
                .build();

    }

    @PutMapping("/{id}")
    ApiResponse<SupplierResponse> update(
            @RequestBody SupplierUpdateRequest supplierUpdateRequest,
            @PathVariable String id){
        return ApiResponse.<SupplierResponse>builder()
                .result(supplierService.update(supplierUpdateRequest, id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Boolean> delete(@PathVariable String id){
        return ApiResponse.<Boolean>builder()
                .result(supplierService.delete(id))
                .build();
    }


}
