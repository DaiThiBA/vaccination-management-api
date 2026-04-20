package com.finalproject.vaccine_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "vaccination_schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinationSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // 🔗 Many schedule → 1 vaccine
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccine_id", nullable = false)
    private Vaccine vaccine;

    @Column(name = "schedule_date", nullable = false)
    private LocalDate scheduleDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private Integer capacity;

    @Column(name = "booked_count")
    private Integer bookedCount = 0;

    @Column
    private String location;
}
