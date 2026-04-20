package com.finalproject.vaccine_management.dto.request.booking;

import com.finalproject.vaccine_management.entity.BookingStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class BookingFilterRequest {
    private String userId;

    private BookingStatus status;

    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    private String keyword;
}
