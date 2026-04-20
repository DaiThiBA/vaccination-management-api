package com.finalproject.vaccine_management.controller;


import com.finalproject.vaccine_management.dto.request.batch.BatchCreationRequest;
import com.finalproject.vaccine_management.dto.request.batch.BatchFilterRequest;
import com.finalproject.vaccine_management.dto.request.batch.BatchUpdateRequest;
import com.finalproject.vaccine_management.dto.response.ApiResponse;
import com.finalproject.vaccine_management.dto.response.BatchResponse;
import com.finalproject.vaccine_management.service.IBatchService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/batches")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BatchController {

    IBatchService batchService;

    @PostMapping()
    ApiResponse<BatchResponse> create(@RequestBody BatchCreationRequest request){
        return ApiResponse.<BatchResponse>builder()
                .result(batchService.create(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<BatchResponse> update(
            @PathVariable String id,
            @RequestBody BatchUpdateRequest request
    ){
        return ApiResponse.<BatchResponse>builder()
                .result(batchService.update(id,request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Boolean> delete(@PathVariable String id){
        return ApiResponse.<Boolean>builder()
                .result(batchService.delete(id))
                .build();
    }

    @GetMapping()
    ApiResponse<Page<BatchResponse>> filter(
            @ModelAttribute BatchFilterRequest filter,
            Pageable pageable
    ){
        return ApiResponse.<Page<BatchResponse>>builder()
                .result(batchService.filter(filter, pageable))
                .build();
    }



}
