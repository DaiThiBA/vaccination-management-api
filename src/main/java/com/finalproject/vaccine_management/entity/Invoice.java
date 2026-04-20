package com.finalproject.vaccine_management.entity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "booking_id", unique = true)
    private Booking booking;

    private BigDecimal totalAmount;

    private String paymentMethod;

    private String paymentStatus;

    private LocalDateTime createdAt;
}
