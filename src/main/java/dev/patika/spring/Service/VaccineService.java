package dev.patika.spring.Service;

import dev.patika.spring.Dto.Request.VaccineRequest;
import dev.patika.spring.Dto.Response.VaccineResponse;
import dev.patika.spring.Entity.Animal;
import dev.patika.spring.Entity.Report;
import dev.patika.spring.Entity.Vaccine;
import dev.patika.spring.Repository.AnimalRepo;
import dev.patika.spring.Repository.ReportRepo;
import dev.patika.spring.Repository.VaccineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VaccineService {
    private final VaccineRepo vaccineRepository;
    private final AnimalRepo animalRepo;
    private  final ReportRepo reportRepo;

    @Autowired
    public VaccineService(VaccineRepo vaccineRepository, AnimalRepo animalRepo, ReportRepo reportRepo) {
        this.vaccineRepository = vaccineRepository;
        this.animalRepo = animalRepo;
        this.reportRepo = reportRepo;
    }



    public List<Vaccine> getVaccinesByAnimalId(Long id) {
        return vaccineRepository.findByAnimal_Id(id);
    }

    public List<Animal> getAnimalsWithExpiringVaccines(LocalDate startDate, LocalDate endDate) {
        return vaccineRepository.findAnimalsWithExpiringVaccines(startDate, endDate);
    }

    //aşı kaydetme
    public VaccineResponse saveVaccine(VaccineRequest vaccineRequest) {
        if (vaccineRequest.getReport() == null || vaccineRequest.getName() == null ||
                vaccineRequest.getAnimal() == null || vaccineRequest.getName().isEmpty() ||
                vaccineRequest.getCode() == null || vaccineRequest.getCode().isEmpty() ||
                vaccineRequest.getProtectionFinishDate() == null || vaccineRequest.getProtectionStartDate()==null ||
                vaccineRequest.getAnimal().getId() == null || vaccineRequest.getReport().getId() ==null
        ) {
            throw new IllegalArgumentException("Aşıya ait alanlar boş olamaz.");
        }
        Vaccine vaccine = new Vaccine();
        vaccine.setName(vaccineRequest.getName());
        vaccine.setCode(vaccineRequest.getCode());
        vaccine.setProtectionStartDate(vaccineRequest.getProtectionStartDate());
        vaccine.setProtectionFinishDate(vaccineRequest.getProtectionFinishDate());

        Animal animal = animalRepo.findById(vaccineRequest.getAnimal().getId())
                .orElseThrow(() -> new RuntimeException("Belirtilen ID'ye sahip hayvan bulunamadı."));
        vaccine.setAnimal(animal);
        Report report = reportRepo.findById(vaccineRequest.getReport().getId())
                .orElseThrow(() -> new RuntimeException("Belirtilen ID'ye sahip rapor bulunamadı."));
        vaccine.setReport(report);

        List<Vaccine> vaccines = vaccineRepository.findByAnimalIdAndNameAndCode(vaccineRequest.getAnimal().getId(),vaccineRequest.getName(),vaccineRequest.getCode());

        if (!vaccines.isEmpty()) {
            throw new RuntimeException("Aynı tarihlerde aynı hayvana aynı aşıyı tekrar ekleyemezsiniz.");
        }
        //DEĞERLENDİRME FORMU 19
        if (vaccine.getProtectionFinishDate().isBefore(vaccine.getProtectionStartDate())) {
            throw new RuntimeException("Koruma bitiş tarihi koruma başlangıç tarihinden önce olamaz.");
        }

        vaccineRepository.save(vaccine);

        VaccineResponse vaccineResponse = new VaccineResponse();
        vaccineResponse.setId(vaccine.getId());
        vaccineResponse.setName(vaccine.getName());
        vaccineResponse.setCode(vaccine.getCode());
        vaccineResponse.setProtectionStartDate(vaccine.getProtectionStartDate());
        vaccineResponse.setProtectionFinishDate(vaccine.getProtectionFinishDate());

        // Animal nesnesini kontrol et ve eğer null değilse atamaları yap
        if (vaccine.getAnimal() != null) {
            long animalId = vaccine.getAnimal().getId();
            Optional<Animal> animalVaccine = animalRepo.findById(animalId);
            vaccineResponse.setAnimal(animalVaccine.orElse(null));
        }
        if (vaccine.getReport() != null) {
            long reportId = vaccine.getReport().getId();
            Optional<Report> reportVaccine = reportRepo.findById(reportId);
            vaccineResponse.setReport(reportVaccine.orElse(null));
        }

        return vaccineResponse;
    }
    public VaccineResponse updateVaccine(Long id, VaccineRequest vaccineRequest) {
        // Belirtilen ID'ye sahip aşıyı bul
        Vaccine vaccine = vaccineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Belirtilen ID'ye sahip aşı bulunamadı."));

        if (vaccineRequest.getReport() == null || vaccineRequest.getName() == null ||
                vaccineRequest.getAnimal() == null || vaccineRequest.getName().isEmpty() ||
                vaccineRequest.getCode() == null || vaccineRequest.getCode().isEmpty() ||
                vaccineRequest.getProtectionFinishDate() == null || vaccineRequest.getProtectionStartDate()==null ||
                vaccineRequest.getAnimal().getId() == null || vaccineRequest.getReport().getId() ==null
        ) {
            throw new IllegalArgumentException("Aşıya ait alanlar boş olamaz.");
        }

        // Aşıyı güncelle
        vaccine.setName(vaccineRequest.getName());
        vaccine.setCode(vaccineRequest.getCode());
        vaccine.setProtectionStartDate(vaccineRequest.getProtectionStartDate());
        vaccine.setProtectionFinishDate(vaccineRequest.getProtectionFinishDate());

        // Aşıya ait hayvanı kontrol et ve eğer null değilse atamaları yap
        if (vaccineRequest.getAnimal() != null) {
            Animal animal = animalRepo.findById(vaccineRequest.getAnimal().getId())
                    .orElseThrow(() -> new RuntimeException("Belirtilen ID'ye sahip hayvan bulunamadı."));
            vaccine.setAnimal(animal);
        }
        if (vaccineRequest.getReport() != null) {
            Report report = reportRepo.findById(vaccineRequest.getReport().getId())
                    .orElseThrow(() -> new RuntimeException("Belirtilen ID'ye sahip rapor bulunamadı."));
            vaccine.setReport(report);
        }


        // Belirtilen aşıya sahip olmayan diğer aşıları kontrol et
        List<Vaccine> vaccines = vaccineRepository.findByAnimalIdAndNameAndCodeAndIdNot(vaccineRequest.getAnimal().getId(), vaccineRequest.getName(), vaccineRequest.getCode(), id);

        if (!vaccines.isEmpty()) {
            throw new RuntimeException("Aynı tarihlerde aynı hayvana aynı aşıyı tekrar ekleyemezsiniz.");
        }
        if (vaccineRequest.getProtectionFinishDate().isBefore(vaccineRequest.getProtectionStartDate())) {
            throw new RuntimeException("Koruma bitiş tarihi koruma başlangıç tarihinden önce olamaz.");
        }

        // Aşıyı güncelle

        vaccineRepository.save(vaccine);

        // Aşıya ait bilgileri oluştur ve döndür
        VaccineResponse vaccineResponse = new VaccineResponse();
        vaccineResponse.setId(vaccine.getId());
        vaccineResponse.setName(vaccine.getName());
        vaccineResponse.setCode(vaccine.getCode());
        vaccineResponse.setProtectionStartDate(vaccine.getProtectionStartDate());
        vaccineResponse.setProtectionFinishDate(vaccine.getProtectionFinishDate());

        // Aşıya ait hayvan bilgisini kontrol et ve eğer null değilse atamaları yap
        if (vaccine.getAnimal() != null) {
            long animalId = vaccine.getAnimal().getId();
            Optional<Animal> animalVaccine = animalRepo.findById(animalId);
            vaccineResponse.setAnimal(animalVaccine.orElse(null));
        }
        if (vaccine.getReport() != null) {
            long reportId = vaccine.getReport().getId();
            Optional<Report> reportVaccine = reportRepo.findById(reportId);
            vaccineResponse.setReport(reportVaccine.orElse(null));
        }

        return vaccineResponse;
    }


    public boolean isReportExist(Long reportId) {
        return reportRepo.existsById(reportId);
    }



    public boolean isAnimalExist(Long animalId) {
        return animalRepo.existsById(animalId);
    }


}
