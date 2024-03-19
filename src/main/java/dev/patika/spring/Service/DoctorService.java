package dev.patika.spring.Service;

import dev.patika.spring.Repository.DoctorRepo;
import dev.patika.spring.Repository.VaccineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {
    private final DoctorRepo doctorRepository;
    @Autowired
    public DoctorService(DoctorRepo doctorRepository) {
        this.doctorRepository = doctorRepository;
    }
}
