package com.finalproject.vaccine_management.entity;

public enum BookingStatus {
    PENDING,      // Đang chờ xác nhận
    CONFIRMED,    // Đã xác nhận
    COMPLETED,    // Đã hoàn thành (đã tiêm)
    CANCELLED,    // Đã hủy
    NO_SHOW       // Không đến
}
