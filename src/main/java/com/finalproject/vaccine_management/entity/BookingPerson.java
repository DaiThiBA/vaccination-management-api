package com.finalproject.vaccine_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "booking_person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "guardian_name", length = 100)
    private String guardianName;
}
