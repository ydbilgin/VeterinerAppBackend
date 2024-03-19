package dev.patika.spring.Repository;

import dev.patika.spring.Entity.Animal;
import dev.patika.spring.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Long> {
    // Randevuları tarih aralığına ve doktora göre filtreleme
    List<Appointment> findByAppointmentDateBetweenAndDoctor_Id(LocalDateTime startDate, LocalDateTime endDate, Long id);

    // Randevuları tarih aralığına ve hayvana göre filtreleme
    boolean existsByAppointmentDateAndDoctor_Id(LocalDateTime appointmentDate, long id);
    List<Appointment> findByAppointmentDateBetweenAndAnimal_Id(
            LocalDateTime startDate, LocalDateTime endDate, Long id);
    Optional<Appointment> findByAppointmentDateAndDoctor_IdAndAnimal_Id(LocalDateTime appointmentDate, Long doctorId, Long animalId);

    boolean existsByAppointmentDateAndDoctor_IdAndAnimal_Id(LocalDateTime appointmentDate ,Long doctorId,Long animalId);





}
