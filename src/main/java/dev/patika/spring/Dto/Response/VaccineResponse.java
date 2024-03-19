package dev.patika.spring.Dto.Response;

import dev.patika.spring.Entity.Animal;
import dev.patika.spring.Entity.Report;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VaccineResponse {
    private Long id;
    private String name;
    private String code;
    private LocalDate protectionStartDate;
    private LocalDate protectionFinishDate;
    private boolean isProtectionExpired;
    private Animal animal;
    private Report report;
}

