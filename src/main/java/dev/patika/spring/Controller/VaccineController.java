package dev.patika.spring.Controller;

import dev.patika.spring.Dto.Request.VaccineRequest;
import dev.patika.spring.Dto.Response.VaccineResponse;
import dev.patika.spring.Entity.Animal;
import dev.patika.spring.Entity.Customer;
import dev.patika.spring.Entity.Vaccine;
import dev.patika.spring.Repository.AnimalRepo;
import dev.patika.spring.Repository.ReportRepo;
import dev.patika.spring.Repository.VaccineRepo;
import dev.patika.spring.Service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vaccine")
public class VaccineController {
    private final VaccineService vaccineService;
    private final VaccineRepo vaccineRepository;
    private  final AnimalRepo animalRepo;
    private  final ReportRepo reportRepo;

    @Autowired
    public VaccineController(VaccineService vaccineService, VaccineRepo vaccineRepository, AnimalRepo animalRepo, ReportRepo reportRepo) {
        this.vaccineService = vaccineService;
        this.vaccineRepository = vaccineRepository;
        this.animalRepo = animalRepo;
        this.reportRepo = reportRepo;
    }

    //DEĞERLENDİRME FORMU 20
    @GetMapping("/{id}")
    public ResponseEntity<List<Vaccine>> getVaccinesByAnimalId(@PathVariable Long id) {
        List<Vaccine> vaccines = vaccineService.getVaccinesByAnimalId(id);
        return ResponseEntity.ok(vaccines);
    }
    //DEĞERLENDİRME FORMU 15
    @PostMapping("/save")
    public ResponseEntity<?> createVaccine(@RequestBody VaccineRequest vaccineRequest) {
        try {
            if (!vaccineService.isAnimalExist(vaccineRequest.getAnimal().getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Belirtilen id'de hayvan mevcut değil");
            }
            if (!vaccineService.isReportExist(vaccineRequest.getReport().getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Belirtilen id'de rapor mevcut değil");
            }

            VaccineResponse response = vaccineService.saveVaccine(vaccineRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVaccine(@PathVariable("id") long id) {
        try {
            Optional<Vaccine> optionalVaccine = vaccineRepository.findById(id);

            if (optionalVaccine.isPresent()) {
                vaccineRepository.deleteById(id);
                return ResponseEntity.ok(id + " numaralı aşı silindi.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir aşı bulunamadı."); // Eğer aşı bulunamazsa 404 hatası
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ID'ye sahip aşı silinemedi: " + id + ": " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateVaccine(@PathVariable Long id, @RequestBody VaccineRequest vaccineRequest) {
        try {
            // Veritabanından belirtilen ID'ye sahip aşıyı bul
            Optional<Vaccine> optionalVaccine = vaccineRepository.findById(id);

            if (optionalVaccine.isPresent()) {
                // Aşı bulunduğunda
                Vaccine vaccine = optionalVaccine.get();

                // Aşı bilgilerini güncelle
                vaccine.setName(vaccineRequest.getName());
                vaccine.setCode(vaccineRequest.getCode());
                vaccine.setProtectionStartDate(vaccineRequest.getProtectionStartDate());
                vaccine.setProtectionFinishDate(vaccineRequest.getProtectionFinishDate());

                // Aşıya ait hayvan ID'sini güncelleme işlemine dahil edebilirsiniz
                if (!vaccineService.isAnimalExist(vaccineRequest.getAnimal().getId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Belirtilen id'de hayvan mevcut değil");
                }
                vaccine.setAnimal(animalRepo.findById(vaccineRequest.getAnimal().getId()).orElseThrow(() -> new RuntimeException("Hayvan bulunamadı")));

                if (!vaccineService.isReportExist(vaccineRequest.getReport().getId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Belirtilen id'de rapor mevcut değil");
                }
                vaccine.setReport(reportRepo.findById(vaccineRequest.getReport().getId()).orElseThrow(() -> new RuntimeException("Rapor bulunamadı")));
                // Aşıyı güncelle ve güncellenmiş aşıyı döndür
                VaccineResponse updatedVaccine = vaccineService.updateVaccine(id, vaccineRequest);
                return ResponseEntity.ok(updatedVaccine);
            } else {
                // Belirtilen ID'ye sahip aşı bulunamadığında
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir aşı bulunamadı.");
            }
        } catch (Exception e) {
            // Herhangi bir hata oluştuğunda
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ID'ye sahip aşı güncellenemedi: " + id + ": " + e.getMessage());
        }
    }







    @GetMapping("/find-all")
    public List<Vaccine> findAll() {
        return this.vaccineRepository.findAll();
    }



    //DEĞERLENDİRME FORMU 21
    @GetMapping("/expiring")
    public ResponseEntity<List<Animal>> getAnimalsWithExpiringVaccines(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        List<Animal> animals = vaccineService.getAnimalsWithExpiringVaccines(startDate, endDate);
        return ResponseEntity.ok(animals);
    }

}
