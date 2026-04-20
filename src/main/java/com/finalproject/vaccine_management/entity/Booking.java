package com.finalproject.vaccine_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private VaccinationSchedule schedule;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "guardian_name", length = 100)
    private String guardianName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private BookingStatus status;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
