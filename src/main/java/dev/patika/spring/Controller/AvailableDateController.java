package dev.patika.spring.Controller;


import dev.patika.spring.Dto.Request.AvailableDateRequest;
import dev.patika.spring.Entity.Appointment;
import dev.patika.spring.Entity.AvailableDate;
import dev.patika.spring.Entity.Doctor;
import dev.patika.spring.Repository.AvailableDateRepo;
import dev.patika.spring.Repository.DoctorRepo;
import dev.patika.spring.Service.AvailableDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/available-dates")
public class AvailableDateController {
    @Autowired
    private  AvailableDateRepo availableDateRepo;
    @Autowired

    private  AvailableDateService availableDateService;
    @Autowired

    private  DoctorRepo doctorRepository;
    AvailableDate availableDate;




    @GetMapping("/{id}")
    public List<AvailableDate> findByDoctorId(@PathVariable("id") long id) {
        return availableDateService.getAvailableDates(id);
    }
    //DEĞERLENDİRME FORMU 13
    @PostMapping("/save")
    public ResponseEntity<AvailableDate> save(@RequestBody AvailableDateRequest request) {
        if (request.getDoctor() == null || request.getDoctor().getId() == null || request.getAvailableDate() == null) {
            throw new RuntimeException("Doktor veya müsait gün boş olamaz!");
        }

        AvailableDate availableDate = new AvailableDate();
        availableDate.setAvailableDate(request.getAvailableDate());

        Long doctorId = request.getDoctor().getId();
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Belirtilen id'ye sahip doktor bulunamadı: " + doctorId));
        availableDate.setDoctor(doctor);

        // Daha önce aynı doktor ve tarihle kayıt yapılmış mı kontrol et
        if (availableDateRepo.existsByDoctorAndAvailableDate(doctor, availableDate.getAvailableDate())) {
            throw new RuntimeException("Bu tarih için zaten bir kayıt var.");
        }


        // Bu noktaya kadar bir hata yoksa kaydı yap
        availableDateRepo.save(availableDate);
        return ResponseEntity.ok(availableDate);
    }
    @PutMapping ("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id,@RequestBody AvailableDateRequest availableDateRequest) {
        try {
            if (availableDateRequest.getDoctor() == null || availableDateRequest.getDoctor().getId() == null || availableDateRequest.getAvailableDate() == null) {
                throw new RuntimeException("Doktor veya müsait gün boş olamaz!");
            }
            Optional<AvailableDate> optionalAvailableDate = availableDateRepo.findById(id);

            if (optionalAvailableDate.isPresent()) {
                AvailableDate existingAvailableDate = optionalAvailableDate.get();

                // Doktorun bu tarihte çalışıp çalışmadığını kontrol et
                if (!availableDateRepo.existsByDoctorIdAndAvailableDate(availableDateRequest.getDoctor().getId(),availableDateRequest.getAvailableDate())) {
                    existingAvailableDate.setAvailableDate(availableDateRequest.getAvailableDate());
                }else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Doktor zaten bu tarihte çalışıyor.");
                }


                // Güncellenen doktoru set et
                existingAvailableDate.setDoctor(doctorRepository.findById(availableDateRequest.getDoctor().getId()).orElseThrow(() -> new RuntimeException("Doktor bulunamadı!")));

                // Güncellenen çalışma tarihini kaydet
                AvailableDate savedAvailableDate = availableDateRepo.save(existingAvailableDate);

                return ResponseEntity.ok(savedAvailableDate);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir çalışma günü bulunamadı.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Çalışma günü güncellenemedi : "  + e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAvailableDate(@PathVariable("id") long id) {
        try {
            Optional<AvailableDate> optionalAvailableDate = availableDateRepo.findById(id);

            if (optionalAvailableDate.isPresent()) {
                availableDateRepo.deleteById(id);
                return ResponseEntity.ok(id + " numaralı çalışma tarihi silindi.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir çalışma tarihi bulunamadı."); // Eğer müsait tarih bulunamazsa 404 hatası
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ID'ye sahip çalışma tarihi silinemedi: " + id + ": " + e.getMessage());
        }
    }

}

