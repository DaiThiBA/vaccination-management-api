package com.finalproject.vaccine_management.mapper;
import com.finalproject.vaccine_management.dto.request.feedback.FeedbackCreationRequest;
import com.finalproject.vaccine_management.dto.response.FeedbackResponse;
import com.finalproject.vaccine_management.entity.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true) // set ở service (lấy từ auth)
    @Mapping(target = "medicalRecord", ignore = true) // set ở service (fetch từ DB)
    Feedback fromCreate(FeedbackCreationRequest request);


    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullName", target = "userName") // hoặc username tùy entity bạn
    @Mapping(source = "medicalRecord.id", target = "medicalRecordId")
    @Mapping(source = "medicalRecord.booking.schedule.vaccine.id", target = "vaccineId")
    @Mapping(source = "medicalRecord.booking.schedule.vaccine.vaccineName", target = "vaccineName")
    FeedbackResponse toResponse(Feedback feedback);
}
