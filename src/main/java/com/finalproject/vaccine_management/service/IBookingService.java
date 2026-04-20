package com.finalproject.vaccine_management.service;

import com.finalproject.vaccine_management.dto.request.booking.BookingCreationRequest;
import com.finalproject.vaccine_management.dto.request.booking.BookingFilterRequest;
import com.finalproject.vaccine_management.dto.request.booking.BookingUpdateRequest;
import com.finalproject.vaccine_management.dto.response.BookingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookingService {
    BookingResponse create(BookingCreationRequest request);
    Page<BookingResponse> filter(BookingFilterRequest request, Pageable pageable);

    BookingResponse updateStatus(String id, BookingUpdateRequest request);
}
