package com.finalproject.vaccine_management.service;

import com.finalproject.vaccine_management.dto.request.batch.BatchCreationRequest;
import com.finalproject.vaccine_management.dto.request.batch.BatchFilterRequest;
import com.finalproject.vaccine_management.dto.request.batch.BatchUpdateRequest;
import com.finalproject.vaccine_management.dto.response.BatchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBatchService {

    BatchResponse create(BatchCreationRequest request);

    BatchResponse update(String id, BatchUpdateRequest request);

    Boolean delete(String id);

    Page<BatchResponse> filter(BatchFilterRequest filter, Pageable pageable);
}
