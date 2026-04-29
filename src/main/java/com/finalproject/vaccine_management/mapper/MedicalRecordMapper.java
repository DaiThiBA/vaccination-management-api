package com.finalproject.vaccine_management.mapper;

import com.finalproject.vaccine_management.dto.response.MedicalResponse;
import com.finalproject.vaccine_management.entity.MedicalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {

    // booking
    @Mapping(source = "booking.id", target = "bookingId")
    // patient (từ booking.user)
    @Mapping(source = "booking.user.id", target = "patientId")
    @Mapping(source = "booking.user.fullName", target = "patientName")
    // vaccine (từ booking.schedule.vaccine)
    @Mapping(source = "booking.schedule.vaccine.id", target = "vaccineId")
    @Mapping(source = "booking.schedule.vaccine.vaccineName", target = "vaccineName")
    // injection
    @Mapping(source = "injectionDate", target = "injectionDate")
    @Mapping(source = "reaction", target = "reaction")

    // batch
    @Mapping(source = "batch.id", target = "batchId")
    @Mapping(source = "batch.batchNumber", target = "batchNumber")

    // staff
    @Mapping(source = "staff.id", target = "staffId")
    @Mapping(source = "staff.fullName", target = "staffName")
    MedicalResponse toResponse(MedicalRecord record);
}
