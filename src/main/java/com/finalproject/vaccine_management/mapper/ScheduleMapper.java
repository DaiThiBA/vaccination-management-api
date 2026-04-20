package com.finalproject.vaccine_management.mapper;

import com.finalproject.vaccine_management.dto.request.vacccinationSchedule.ScheduleCreationRequest;
import com.finalproject.vaccine_management.dto.request.vacccinationSchedule.ScheduleUpdateRequest;
import com.finalproject.vaccine_management.dto.request.vaccine.VaccineCreationRequest;
import com.finalproject.vaccine_management.dto.response.VaccineScheduleResponse;
import com.finalproject.vaccine_management.entity.VaccinationSchedule;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    VaccinationSchedule fromCreate(ScheduleCreationRequest request);

    @Mapping(target = "vaccineId", source = "vaccine.id")
    @Mapping(target = "vaccineName", source = "vaccine.vaccineName")
    VaccineScheduleResponse toResponse(VaccinationSchedule entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSchedule(@MappingTarget VaccinationSchedule vaccinationSchedule, ScheduleUpdateRequest req);

}
