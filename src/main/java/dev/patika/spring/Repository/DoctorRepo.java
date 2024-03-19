package dev.patika.spring.Repository;

import dev.patika.spring.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Long> {

    Doctor findByName(String name);


    @Query("SELECT COUNT(a) > 0 FROM AvailableDate a " +
            "WHERE a.doctor.id = :id " +
            "AND a.availableDate = :availableDate")
    boolean isDoctorAvailableOnDate(@Param("id") Long id, @Param("availableDate") LocalDate availableDate);

    boolean existsByPhone(String phone);







}
