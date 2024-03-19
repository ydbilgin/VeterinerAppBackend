package dev.patika.spring.Controller;

import dev.patika.spring.Dto.Request.AppointmentRequest;
import dev.patika.spring.Dto.Request.DoctorRequest;
import dev.patika.spring.Dto.Request.ReportRequest;
import dev.patika.spring.Entity.Animal;
import dev.patika.spring.Entity.Appointment;
import dev.patika.spring.Entity.Doctor;
import dev.patika.spring.Entity.Report;
import dev.patika.spring.Repository.AnimalRepo;
import dev.patika.spring.Repository.AppointmentRepo;
import dev.patika.spring.Repository.DoctorRepo;
import dev.patika.spring.Service.AppointmentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {


    private final AppointmentService appointmentService;
    private final AppointmentRepo appointmentRepo;
    private final DoctorRepo doctorRepo;
    private final AnimalRepo animalRepo;

    public AppointmentController(AppointmentService appointmentService, AppointmentRepo appointmentRepo, DoctorRepo doctorRepo, AnimalRepo animalRepo) {
        this.appointmentService = appointmentService;
        this.appointmentRepo = appointmentRepo;
        this.doctorRepo = doctorRepo;
        this.animalRepo = animalRepo;
    }


    @GetMapping("/{id}")
    public Appointment findById(@PathVariable("id") long id) {
        return appointmentService.getAppointment(id);
    }

    //DEĞERLENDİRME FORMU 14

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        try {
            Appointment response = appointmentService.createAppointment(appointmentRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable("id") long id, @RequestBody AppointmentRequest appointmentRequest) {
        try {
            Optional<Appointment> optionalAppointment = appointmentRepo.findById(id);

            if (!optionalAppointment.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir randevu bulunamadı.");
            }

            Appointment appointment = optionalAppointment.get();

            appointment.setAppointmentDate(appointmentRequest.getAppointmentDate());

            if (appointmentRequest.getDoctor() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Doktor bilgisi eksik.");
            }
            if (appointmentRequest.getAnimal() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hayvan bilgisi eksik.");
            }

            if (!appointmentService.isDoctorExist(appointmentRequest.getDoctor().getId())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Belirtilen ID'de bir doktor bulunmuyor.");
            }
            if (!appointmentService.isAnimalExist(appointmentRequest.getAnimal().getId())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Belirtilen ID'de bir hayvan bulunmuyor.");
            }

            appointment.setDoctor(doctorRepo.findById(appointmentRequest.getDoctor().getId()).orElseThrow(() -> new RuntimeException("Doktor bulunamadı!")));
            appointment.setAnimal(animalRepo.findById(appointmentRequest.getAnimal().getId()).orElseThrow(() -> new RuntimeException("Hayvan bulunamadı!")));

            Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentRequest);

            return ResponseEntity.ok(updatedAppointment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Randevu güncellenemedi: "  + e.getMessage());
        }
    }





    //http://localhost:8080/appointment/findByDateAndAnimal?startDate=2023-12-17&endDate=2023-12-25&id=3

    //örnek çalışma ^

    //DEĞERLENDİRME FORMU 23
    @GetMapping("/findByDateAndAnimal")
    public ResponseEntity<List<Appointment>> findAppointmentsByDateAndAnimal(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam Long id) {
        List<Appointment> appointments = appointmentService.findAppointmentsByDateAndAnimal(startDate, endDate, id);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    //http://localhost:8080/appointment/findByDateAndDoctor?startDate=2023-12-17&endDate=2023-12-25&id=1

    //DEĞERLENDİRME FORMU 24
    @GetMapping("/findByDateAndDoctor")
    public ResponseEntity<List<Appointment>> findAppointmentsByDateAndDoctor(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam Long id) {
        List<Appointment> appointments = appointmentService.findAppointmentsByDateAndDoctor(startDate,endDate, id);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable("id") long id) {
        try {
            Optional<Appointment> optionalAppointment = appointmentRepo.findById(id);

            if (optionalAppointment.isPresent()) {
                appointmentRepo.deleteById(id);
                return ResponseEntity.ok(id + " numaralı randevu silindi.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir randevu bulunamadı."); // Eğer randevu bulunamazsa 404 hatası
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ID'ye sahip randevu silinemedi: " + id + ": " + e.getMessage());
        }
    }






    // Randevuların listesini döndüren metot
    @GetMapping("/find-all")
    public List<Appointment> findAll() {
        return appointmentService.findAllAppointments();
    }


}
