package com.finalproject.vaccine_management.service.Impl;

import com.finalproject.vaccine_management.dto.request.supplier.SupplierCreationRequest;
import com.finalproject.vaccine_management.dto.request.supplier.SupplierFilterRequest;
import com.finalproject.vaccine_management.dto.request.supplier.SupplierUpdateRequest;
import com.finalproject.vaccine_management.dto.response.SupplierResponse;
import com.finalproject.vaccine_management.entity.Supplier;
import com.finalproject.vaccine_management.exception.AppException;
import com.finalproject.vaccine_management.exception.ErrorCode;
import com.finalproject.vaccine_management.mapper.SupplierMapper;
import com.finalproject.vaccine_management.repository.ISupplierRepository;
import com.finalproject.vaccine_management.service.ISupplierService;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SupplierService implements ISupplierService {

    ISupplierRepository supplierRepository;
    SupplierMapper supplierMapper;


    @Override
    public SupplierResponse create(SupplierCreationRequest request) {
        if(supplierRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.SUPPLIER_EXISTED);
        }

        Supplier supplier = supplierMapper.fromCreate(request);

        return supplierMapper.toSupplierResponse(supplierRepository.save(supplier));
    }

    @Override
    public Page<SupplierResponse> filter(SupplierFilterRequest filter, Pageable pageable) {
        Specification<Supplier> spec = buildFilter(filter);

        Page<Supplier> supplierPage = supplierRepository.findAll(spec,pageable);

        List<SupplierResponse> list = new ArrayList<>();
        for(Supplier supplier : supplierPage){
            list.add(supplierMapper.toSupplierResponse(supplier));
        }

        return new PageImpl<>(list, pageable, supplierPage.getTotalElements());
    }

    @Override
    @Transactional
    public SupplierResponse update(SupplierUpdateRequest supplierUpdateRequest, String id) {
        var supplier = supplierRepository.findById(id)
                .orElseThrow( () -> new AppException(ErrorCode.SUPPLIER_NOT_FOUND));

        supplierMapper.updateSupplier(supplier,supplierUpdateRequest);

        return supplierMapper.toSupplierResponse(supplierRepository.save(supplier));
    }

    @Override
    @Transactional
    public Boolean delete(String id) {
        var supplier = supplierRepository.findById(id)
                .orElseThrow( () -> new AppException(ErrorCode.SUPPLIER_NOT_FOUND));

        try {
            supplierRepository.delete(supplier);
            return true;
        }
        catch (DataIntegrityViolationException ex){
            throw new AppException(ErrorCode.SUPPLIER_CANNOT_DELETE);
        }
    }

    public Specification<Supplier> buildFilter(SupplierFilterRequest request) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
                String keyword = "%" + request.getKeyword().toLowerCase() + "%";

                Predicate nameLike = cb.like(cb.lower(root.get("name")), keyword);
                Predicate contactLike = cb.like(cb.lower(root.get("contactInfo")), keyword);
                Predicate addressLike = cb.like(cb.lower(root.get("address")), keyword);

                predicates.add(cb.or(nameLike, contactLike, addressLike));
            }

            if (request.getName() != null && !request.getName().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("name")),
                        "%" + request.getName().toLowerCase() + "%"
                ));
            }

            if (request.getContactInfo() != null && !request.getContactInfo().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("contactInfo")),
                        "%" + request.getContactInfo().toLowerCase() + "%"
                ));
            }

            if (request.getAddress() != null && !request.getAddress().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("address")),
                        "%" + request.getAddress().toLowerCase() + "%"
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
