package com.finalproject.vaccine_management.service.Impl;

import com.finalproject.vaccine_management.dto.request.medicalRecord.MedicalRecordCreationRequest;
import com.finalproject.vaccine_management.dto.response.MedicalResponse;
import com.finalproject.vaccine_management.entity.Batch;
import com.finalproject.vaccine_management.entity.Booking;
import com.finalproject.vaccine_management.entity.MedicalRecord;
import com.finalproject.vaccine_management.entity.User;
import com.finalproject.vaccine_management.exception.AppException;
import com.finalproject.vaccine_management.exception.ErrorCode;
import com.finalproject.vaccine_management.mapper.MedicalRecordMapper;
import com.finalproject.vaccine_management.repository.IBatchRepository;
import com.finalproject.vaccine_management.repository.IBookingRepository;
import com.finalproject.vaccine_management.repository.IMedicalRepository;
import com.finalproject.vaccine_management.repository.IUserRepository;
import com.finalproject.vaccine_management.service.IMedicalRecordService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MedicalRecordService implements IMedicalRecordService {

    IMedicalRepository medicalRepository;
    IBookingRepository bookingRepository;
    IBatchRepository batchRepository;
    IUserRepository userRepository;
    MedicalRecordMapper medicalRecordMapper;

    @Override
    @Transactional
    public MedicalResponse create(MedicalRecordCreationRequest request) {

        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow( () -> new AppException(ErrorCode.BOOKING_NOT_FOUND));

        Batch batch = batchRepository.findById(request.getBatchId())
                 .orElseThrow(() -> new AppException(ErrorCode.BATCH_NOT_FOUND));

        User staff = userRepository.findById(request.getStaffId())
                 .orElseThrow( () -> new AppException(ErrorCode.USER_NOT_FOUND));


        MedicalRecord medicalRecord = MedicalRecord.builder()
                .batch(batch)
                .booking(booking)
                .staff(staff)
                .reaction(request.getReaction())
                .injectionDate(request.getInjectionDate())
                .build();

        return medicalRecordMapper.toResponse(medicalRepository.save(medicalRecord));
    }
}
