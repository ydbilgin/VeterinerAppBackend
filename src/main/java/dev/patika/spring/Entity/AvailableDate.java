package dev.patika.spring.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "available_date")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AvailableDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "available_date_id" , columnDefinition = "serial")
    private Long id;
    @Temporal(TemporalType.DATE)
    @Column(name = "available_date" , nullable = false)
    private LocalDate availableDate;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

}
