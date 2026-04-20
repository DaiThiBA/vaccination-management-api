package com.finalproject.vaccine_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "vaccine")
@Getter
@Setter
@NoArgsConstructor
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "vaccine_name", nullable = false )
    private String vaccineName;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
}
