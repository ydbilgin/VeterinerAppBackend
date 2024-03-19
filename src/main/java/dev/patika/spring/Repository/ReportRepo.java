package dev.patika.spring.Repository;

import dev.patika.spring.Entity.Appointment;
import dev.patika.spring.Entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepo extends JpaRepository<Report,Long> {
    Appointment findByAppointment_Id(Long id);

    boolean existsByTitleAndAppointment_Id(String title ,Long id);











}
