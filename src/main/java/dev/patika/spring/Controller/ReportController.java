package dev.patika.spring.Controller;

import dev.patika.spring.Dto.Request.CustomerRequest;
import dev.patika.spring.Dto.Request.ReportRequest;
import dev.patika.spring.Entity.Animal;
import dev.patika.spring.Entity.Appointment;
import dev.patika.spring.Entity.Customer;
import dev.patika.spring.Entity.Report;
import dev.patika.spring.Repository.AppointmentRepo;
import dev.patika.spring.Repository.ReportRepo;
import dev.patika.spring.Service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportRepo reportRepo;
    private final AppointmentRepo appointmentRepo;
    private  final ReportService reportService;




    public ReportController(ReportRepo reportRepo, AppointmentRepo appointmentRepo, ReportService reportService) {
        this.reportRepo = reportRepo;
        this.appointmentRepo = appointmentRepo;
        this.reportService = reportService;
    }

    @GetMapping("/{id}")
    public Report findbyId(@PathVariable("id") long id) {
        return this.reportRepo.findById(id).orElseThrow();
    }

    @GetMapping("/find-all")
    public List<Report> findAll(){
        return this.reportRepo.findAll();
    }



    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Report report)
    {
        if (report.getTitle() == null || report.getTitle().isEmpty() ||
                report.getDiagnosis() == null ||report.getDiagnosis().isEmpty() ||
                report.getPrice() == null ||
                report.getAppointment() == null ||report.getAppointment().getId()==null
                ) {
            throw new IllegalArgumentException("Rapora ait alanlar boş olamaz.");
        }
        if (!reportService.isAppointmentExist(report.getAppointment().getId())){
            return ResponseEntity.status((HttpStatus.BAD_REQUEST)).body("Belirtilen ID'de bir randevu bulunmuyor.");
        }

        if (reportRepo.existsByTitleAndAppointment_Id(report.getTitle(),report.getAppointment().getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bu rapor zaten mevcut!");
        }

        Appointment appointment = report.getAppointment();
        if (report.getAppointment() != null) {
            Optional<Appointment> existingAppointment = appointmentRepo.findById(report.getAppointment().getId());
            existingAppointment.ifPresent(report::setAppointment);
        }

        Report savedReport = reportRepo.save(report);
        return ResponseEntity.ok(savedReport);
    }


    //Randevu başka bir rapora bğalıysa kaydetmeme yapılmamalı updatete ve save'de
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReport(@PathVariable("id") long id, @RequestBody ReportRequest reportRequest) {
        try {
            if (reportRequest.getTitle() == null || reportRequest.getTitle().isEmpty() ||
                    reportRequest.getDiagnosis() == null ||reportRequest.getDiagnosis().isEmpty() ||
                    reportRequest.getPrice() == null ||
                    reportRequest.getAppointment() == null ||reportRequest.getAppointment().getId()==null
            ) {
                throw new IllegalArgumentException("Rapora ait alanlar boş olamaz.");
            }
            Optional<Report> optionalReport = reportRepo.findById(id);
            if (reportRequest.getAppointment() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Randevu bilgisi eksik.");
            }

            if (optionalReport.isPresent()) {
                Report report = optionalReport.get();


                report.setTitle(reportRequest.getTitle());
                report.setDiagnosis(reportRequest.getDiagnosis());
                report.setPrice(reportRequest.getPrice());

                if (!reportService.isAppointmentExist(reportRequest.getAppointment().getId())){
                    return ResponseEntity.status((HttpStatus.BAD_REQUEST)).body("Belirtilen ID'de bir randevu bulunmuyor.");
                }
                report.setAppointment(appointmentRepo.findById(reportRequest.getAppointment().getId()).orElseThrow(() -> new RuntimeException("Randevu bulunamadı!")));


                if (!(report.getTitle().equals(reportRequest.getTitle())) || !(report.getAppointment().getId().equals(reportRequest.getAppointment().getId()))){
                    if (reportRepo.existsByTitleAndAppointment_Id(reportRequest.getTitle(),reportRequest.getAppointment().getId())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bu rapor zaten mevcut!");
                    }
                }

                Report updatedReport = reportRepo.save(report);

                return ResponseEntity.ok(updatedReport);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir rapor bulunamadı.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ID'ye sahip rapor güncellenemedi: " + id + ": " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable("id") long id) {
        try {
            Optional<Report> optionalReport = reportRepo.findById(id);

            if (optionalReport.isPresent()) {
                Report report = optionalReport.get();
                reportRepo.deleteById(id);

                return ResponseEntity.ok(report.getTitle() + " başlıklı rapor silindi.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir rapor bulunamadı."); // Eğer rapor bulunamazsa 404 hatası
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ID'ye sahip rapor silinemedi: " + id + ": " + e.getMessage());
        }
    }
}
