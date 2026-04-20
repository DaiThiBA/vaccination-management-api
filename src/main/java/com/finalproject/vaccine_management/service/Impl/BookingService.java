package com.finalproject.vaccine_management.service.Impl;

import com.finalproject.vaccine_management.dto.request.booking.BookingCreationRequest;
import com.finalproject.vaccine_management.dto.request.booking.BookingFilterRequest;
import com.finalproject.vaccine_management.dto.request.booking.BookingUpdateRequest;
import com.finalproject.vaccine_management.dto.response.BookingResponse;
import com.finalproject.vaccine_management.entity.Booking;
import com.finalproject.vaccine_management.entity.BookingStatus;
import com.finalproject.vaccine_management.exception.AppException;
import com.finalproject.vaccine_management.exception.ErrorCode;
import com.finalproject.vaccine_management.mapper.BookingMapper;
import com.finalproject.vaccine_management.repository.IBookingRepository;
import com.finalproject.vaccine_management.repository.IScheduleRepository;
import com.finalproject.vaccine_management.repository.IUserRepository;
import com.finalproject.vaccine_management.service.IBookingService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingService implements IBookingService {

    BookingMapper bookingMapper;
    IBookingRepository bookingRepository;

    IUserRepository userRepository;
    IScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public BookingResponse create(BookingCreationRequest request) {
       var user = userRepository.findById(request.getUserId())
               .orElseThrow( () ->  new AppException(ErrorCode.USER_NOT_FOUND));
        var schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_FOUND));

        if (schedule.getBookedCount() >= schedule.getCapacity()) {
            throw new AppException(ErrorCode.SLOT_FULL);
        }

        Booking booking = bookingMapper.fromBookingCreationRequest(request);
        booking.setSchedule(schedule);
        booking.setUser(user);
        booking.setStatus(BookingStatus.PENDING);

        return bookingMapper.toBookingResponse(bookingRepository.save(booking));
    }

    @Transactional(readOnly = true)
    public Page<BookingResponse> filter(BookingFilterRequest request, Pageable pageable) {

        Specification<Booking> spec = buildFilter(request);

        Page<Booking> bookingPage = bookingRepository.findAll(spec, pageable);

        List<BookingResponse> list = bookingPage.stream()
                .map(bookingMapper::toBookingResponse)
                .toList();

        return new PageImpl<>(list, pageable, bookingPage.getTotalElements());
    }

    @Override
    @Transactional
    public BookingResponse updateStatus(String id, BookingUpdateRequest request) {

        var booking = bookingRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));

        BookingStatus current = booking.getStatus();
        BookingStatus next = request.getStatus();

        if (current == BookingStatus.CANCELLED || current == BookingStatus.COMPLETED) {
            throw new AppException(ErrorCode.INVALID_STATUS_TRANSITION);
        }

        booking.setStatus(next);

        return bookingMapper.toBookingResponse(bookingRepository.save(booking));
    }

    public Specification<Booking> buildFilter(BookingFilterRequest request) {
        return (root, query, cb) -> {

            if (query.getResultType() != Long.class) {
                query.distinct(true);
            }

            var predicates = cb.conjunction();

            // join user
            Join<Booking, Object> userJoin = root.join("user", JoinType.LEFT);

            // join schedule
            Join<Booking, Object> scheduleJoin = root.join("schedule", JoinType.LEFT);

            // join vaccine through schedule
            Join<Object, Object> vaccineJoin = scheduleJoin.join("vaccine", JoinType.LEFT);

            // =========================
            // FILTER
            // =========================

            // userId
            if (request.getUserId() != null) {
                predicates = cb.and(predicates,
                        cb.equal(userJoin.get("id"), request.getUserId()));
            }

            // status
            if (request.getStatus() != null) {
                predicates = cb.and(predicates,
                        cb.equal(root.get("status"), request.getStatus()));
            }

            // fromDate (schedule_date)
            if (request.getFromDate() != null) {
                predicates = cb.and(predicates,
                        cb.greaterThanOrEqualTo(
                                scheduleJoin.get("scheduleDate"),
                                request.getFromDate()));
            }

            // toDate
            if (request.getToDate() != null) {
                predicates = cb.and(predicates,
                        cb.lessThanOrEqualTo(
                                scheduleJoin.get("scheduleDate"),
                                request.getToDate()));
            }

            // keyword (userName OR vaccineName OR person fullName)
            if (request.getKeyword() != null && !request.getKeyword().isBlank()) {

                String pattern = "%" + request.getKeyword().toLowerCase() + "%";

                predicates = cb.and(predicates,
                        cb.or(
                                cb.like(cb.lower(userJoin.get("fullName")), pattern),
                                cb.like(cb.lower(vaccineJoin.get("vaccineName")), pattern),
                                cb.like(cb.lower(root.get("fullName")), pattern)
                        )
                );
            }

            return predicates;
        };
    }
}
