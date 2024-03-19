package dev.patika.spring.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table ( name = "report")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id" , columnDefinition = "serial")
    private Long id;
    @Column(name = "report_title" , length = 100 , nullable = false)
    private String title;
    @Column(name = "report_diagnosis" , length = 100 , nullable = false)
    private String diagnosis;
    @Column(name = "report_price" , nullable = false)
    private Double price;



    @OneToMany(mappedBy = "report",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Vaccine> vaccines;


    @OneToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

}
