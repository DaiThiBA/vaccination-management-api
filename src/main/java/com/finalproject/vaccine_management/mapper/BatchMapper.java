package com.finalproject.vaccine_management.mapper;


import com.finalproject.vaccine_management.dto.request.batch.BatchCreationRequest;
import com.finalproject.vaccine_management.dto.request.batch.BatchUpdateRequest;
import com.finalproject.vaccine_management.dto.response.BatchResponse;
import com.finalproject.vaccine_management.entity.Batch;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BatchMapper {

    Batch fromBatchCreate(BatchCreationRequest batchCreationRequest);

    @Mapping(target = "vaccineId", source = "vaccine.id")
    @Mapping(target = "vaccineName", source = "vaccine.vaccineName")
    BatchResponse toBatchResponse(Batch batch);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBatch(@MappingTarget Batch batch, BatchUpdateRequest request);
}
