package dev.patika.spring.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "vaccine")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccine_id", columnDefinition = "serial")
    private Long id;

    @Column(name = "vaccine_name", length = 100, nullable = false)
    private String name;
    @Column(name = "vaccine_code", length = 55, nullable = false)
    private String code;
    @Temporal(TemporalType.DATE)
    @Column(name = "vaccine_start_date" , nullable = false)
    private LocalDate protectionStartDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "vaccine_finish_date" , nullable = false)
    private LocalDate protectionFinishDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "report_id")
    private Report report;



}
