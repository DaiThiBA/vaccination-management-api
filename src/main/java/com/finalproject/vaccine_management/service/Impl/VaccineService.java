package com.finalproject.vaccine_management.service.Impl;

import com.finalproject.vaccine_management.dto.request.booking.BookingFilterRequest;
import com.finalproject.vaccine_management.dto.request.vaccine.VaccineCreationRequest;
import com.finalproject.vaccine_management.dto.request.vaccine.VaccineFilterRequest;
import com.finalproject.vaccine_management.dto.response.VaccineResponse;
import com.finalproject.vaccine_management.entity.Booking;
import com.finalproject.vaccine_management.entity.Vaccine;
import com.finalproject.vaccine_management.exception.AppException;
import com.finalproject.vaccine_management.exception.ErrorCode;
import com.finalproject.vaccine_management.mapper.VaccineMapper;
import com.finalproject.vaccine_management.repository.ISupplierRepository;
import com.finalproject.vaccine_management.repository.IVaccineRepository;
import com.finalproject.vaccine_management.service.IVaccineService;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
public class VaccineService implements IVaccineService {

    VaccineMapper vaccineMapper;
    ISupplierRepository supplierRepository;
    IVaccineRepository vaccineRepository;

    @Override
    @Transactional
    public VaccineResponse create(VaccineCreationRequest request) {
        var supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new AppException(ErrorCode.SUPPLIER_NOT_FOUND));

        Vaccine vaccine = vaccineMapper.fromVaccineCreation(request);
        vaccine.setSupplier(supplier);
        return vaccineMapper.toVaccineResponse(vaccineRepository.save(vaccine));
    }

    @Override
    public Page<VaccineResponse> filter(VaccineFilterRequest filter, Pageable pageable) {
        Specification<Vaccine> spec = buildFilter(filter);

        Page<Vaccine> vaccinePage = vaccineRepository.findAll(spec, pageable);

        List<VaccineResponse> list = new ArrayList<>();

        for( Vaccine vaccine : vaccinePage.getContent()){
            list.add(vaccineMapper.toVaccineResponse(vaccine));
        }

        return new PageImpl<>(list, pageable, vaccinePage.getTotalElements());
    }



    public Specification<Vaccine> buildFilter(VaccineFilterRequest req) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (query.getResultType() != Long.class) {
                root.fetch("supplier", JoinType.LEFT);
                query.distinct(true);
            }

            if (req.getVaccineName() != null && !req.getVaccineName().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("vaccineName")),
                        "%" + req.getVaccineName().toLowerCase().trim() + "%"
                ));
            }

            // minPrice
            if (req.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("price"),
                        req.getMinPrice()
                ));
            }

            // maxPrice
            if (req.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("price"),
                        req.getMaxPrice()
                ));
            }

            // supplierId
            if (req.getSupplierId() != null && !req.getSupplierId().isBlank()) {
                predicates.add(cb.equal(
                        root.get("supplier").get("id"),
                        req.getSupplierId().trim()
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
