package dev.patika.spring.Repository;

import dev.patika.spring.Entity.AvailableDate;
import dev.patika.spring.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface AvailableDateRepo extends JpaRepository<AvailableDate,Long> {
    boolean existsByDoctorAndAvailableDate(Doctor doctor, LocalDate availableDate);

    List<AvailableDate> findByDoctor_Id(Long id);


    Optional<AvailableDate> findByDoctorIdAndAvailableDate(Long doctorId, LocalDate availableDate);
    boolean existsByDoctorIdAndAvailableDate(Long doctorId, LocalDate availableDate);

}
