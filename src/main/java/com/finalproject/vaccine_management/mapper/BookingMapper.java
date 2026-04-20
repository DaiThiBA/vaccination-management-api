package com.finalproject.vaccine_management.mapper;

import com.finalproject.vaccine_management.dto.request.booking.BookingCreationRequest;
import com.finalproject.vaccine_management.dto.response.BookingResponse;
import com.finalproject.vaccine_management.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Booking fromBookingCreationRequest(BookingCreationRequest request);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullName", target = "userName")
    @Mapping(source = "schedule.id", target = "scheduleId")
    @Mapping(source = "schedule.scheduleDate", target = "scheduleDate")
    @Mapping(source = "schedule.startTime", target = "startTime")
    @Mapping(source = "schedule.endTime", target = "endTime")
    @Mapping(source = "schedule.location", target = "location")
    @Mapping(source = "schedule.vaccine.vaccineName", target = "vaccineName")
    BookingResponse toBookingResponse(Booking booking);
}
