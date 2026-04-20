package com.finalproject.vaccine_management.controller;

import com.finalproject.vaccine_management.dto.request.booking.BookingCreationRequest;
import com.finalproject.vaccine_management.dto.request.booking.BookingFilterRequest;
import com.finalproject.vaccine_management.dto.request.booking.BookingUpdateRequest;
import com.finalproject.vaccine_management.dto.response.ApiResponse;
import com.finalproject.vaccine_management.dto.response.BookingResponse;
import com.finalproject.vaccine_management.dto.response.VaccineResponse;
import com.finalproject.vaccine_management.service.IBookingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingController {

    IBookingService bookingService;

    @PostMapping()
    ApiResponse<BookingResponse> create(@RequestBody BookingCreationRequest request){
        return ApiResponse.<BookingResponse>builder()
                .result(bookingService.create(request))
                .build();
    }

    @GetMapping()
    ApiResponse<Page<BookingResponse>> filter(
            @ModelAttribute BookingFilterRequest filter,
            Pageable pageable
    ){
        return ApiResponse.<Page<BookingResponse>>builder()
                .result(bookingService.filter(filter, pageable))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<BookingResponse> updateStatus(
            @PathVariable String id,
            @RequestBody BookingUpdateRequest request
    ){
        return ApiResponse.<BookingResponse>builder()
                .result(bookingService.updateStatus(id,request))
                .build();
    }

}
