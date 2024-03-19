package dev.patika.spring.Service;

import dev.patika.spring.Entity.AvailableDate;
import dev.patika.spring.Repository.AvailableDateRepo;
import dev.patika.spring.Repository.DoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailableDateService {

    private final AvailableDateRepo availableDateRepo;
    private final DoctorRepo doctorRepo;
    @Autowired
    public AvailableDateService(AvailableDateRepo availableDateRepo, DoctorRepo doctorRepo) {
        this.availableDateRepo = availableDateRepo;
        this.doctorRepo = doctorRepo;
    }

    //Doktor'a ait günleri bulma
    public List<AvailableDate> getAvailableDates(Long doctorId) {
        return availableDateRepo.findByDoctor_Id(doctorId);
    }

    public boolean isDoctorExist(Long doctorId) {
        return doctorRepo.existsById(doctorId);
    }



}
