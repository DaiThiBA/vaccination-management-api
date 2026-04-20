package com.finalproject.vaccine_management.service.Impl;

import com.finalproject.vaccine_management.dto.request.batch.BatchCreationRequest;
import com.finalproject.vaccine_management.dto.request.batch.BatchFilterRequest;
import com.finalproject.vaccine_management.dto.request.batch.BatchUpdateRequest;
import com.finalproject.vaccine_management.dto.response.BatchResponse;
import com.finalproject.vaccine_management.entity.Batch;
import com.finalproject.vaccine_management.exception.AppException;
import com.finalproject.vaccine_management.exception.ErrorCode;
import com.finalproject.vaccine_management.mapper.BatchMapper;
import com.finalproject.vaccine_management.repository.IBatchRepository;
import com.finalproject.vaccine_management.repository.ISupplierRepository;
import com.finalproject.vaccine_management.repository.IVaccineRepository;
import com.finalproject.vaccine_management.service.IBatchService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BatchService implements IBatchService {

    IVaccineRepository vaccineRepository;
    IBatchRepository batchRepository;
    BatchMapper batchMapper;

    @Override
    public BatchResponse create(BatchCreationRequest request) {

        var vaccine = vaccineRepository.findById(request.getVaccineId())
                .orElseThrow(() -> new AppException(ErrorCode.VACCINE_NOT_FOUND));

        Batch batch = batchMapper.fromBatchCreate(request);
        batch.setVaccine(vaccine);
        return batchMapper.toBatchResponse(batchRepository.save(batch));
    }

    @Override
    public BatchResponse update(String id, BatchUpdateRequest request) {
        Batch batch = batchRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BATCH_NOT_FOUND));

        batchMapper.updateBatch(batch, request);

        return batchMapper.toBatchResponse(batchRepository.save(batch));
    }

    @Override
    public Boolean delete(String id) {
        var batch= batchRepository.findById(id)
                .orElseThrow( () -> new AppException(ErrorCode.BATCH_NOT_FOUND));

        batchRepository.delete(batch);
        return true;
    }

    @Override
    public Page<BatchResponse> filter(BatchFilterRequest filter, Pageable pageable) {
        Specification<Batch> spec = buildFilter(filter);

        Page<Batch> batchPage = batchRepository.findAll(spec, pageable);

        List<BatchResponse> list = new ArrayList<>();

        for( Batch batch: batchPage .getContent()){
            list.add(batchMapper.toBatchResponse(batch));
        }

        return new PageImpl<>(list, pageable, batchPage .getTotalElements());
    }


    public Specification<Batch> buildFilter(BatchFilterRequest request) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // 🔍 1. Keyword search (batchNumber + vaccineName)
            if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
                String keyword = "%" + request.getKeyword().trim().toLowerCase() + "%";

                // join vaccine
                Join<Object, Object> vaccineJoin = root.join("vaccine", JoinType.LEFT);

                Predicate batchNumberLike = cb.like(cb.lower(root.get("batchNumber")), keyword);
                Predicate vaccineNameLike = cb.like(cb.lower(vaccineJoin.get("vaccineName")), keyword);

                predicates.add(cb.or(batchNumberLike, vaccineNameLike));
            }

            // 🎯 2. Filter theo vaccineId
            if (request.getVaccineId() != null && !request.getVaccineId().isBlank()) {
                predicates.add(cb.equal(root.get("vaccine").get("id"), request.getVaccineId()));
            }

            // 📦 3. Filter quantity range
            if (request.getMinQuantity() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("quantity"), request.getMinQuantity()));
            }

            if (request.getMaxQuantity() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("quantity"), request.getMaxQuantity()));
            }

            // ⏰ 4. Filter expiry range
            if (request.getExpiryFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("expiryDate"), request.getExpiryFrom()));
            }

            if (request.getExpiryTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("expiryDate"), request.getExpiryTo()));
            }

            // 🔥 IMPORTANT
            if (predicates.isEmpty()) {
                return null;
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
