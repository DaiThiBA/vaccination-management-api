package com.finalproject.vaccine_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "medical_record")
@Getter
@Setter
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User patient;

    @ManyToOne
    @JoinColumn(name = "vaccine_id")
    private Vaccine vaccine;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private User staff;

    private LocalDateTime injectionDate;

    private String reaction;
}