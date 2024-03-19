package dev.patika.spring.Dto.Request;

import dev.patika.spring.Entity.Appointment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportRequest {
    private long id;
    private String title;
    private String diagnosis;
    private Double price;
    private Appointment appointment;


}
