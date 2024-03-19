package dev.patika.spring.Dto.Request;

import dev.patika.spring.Entity.Doctor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AvailableDateRequest {
    private Long id;
    private LocalDate availableDate;
    private Doctor doctor;
}
