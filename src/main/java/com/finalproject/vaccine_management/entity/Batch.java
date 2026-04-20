package com.finalproject.vaccine_management.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "batch")
@Getter
@Setter
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "vaccine_id", nullable = false)
    private Vaccine vaccine;

    @Column(name = "batch_number", unique = true)
    private String batchNumber;

    @Column(name = "manufacture_date")
    private LocalDate manufactureDate;

    @Column(name ="expiry_date")
    private LocalDate expiryDate;

    @Column(name = "quantity")
    private Integer quantity;
}
