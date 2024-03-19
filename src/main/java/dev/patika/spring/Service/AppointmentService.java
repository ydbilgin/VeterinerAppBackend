package dev.patika.spring.Service;

import dev.patika.spring.Dto.Request.AppointmentRequest;
import dev.patika.spring.Entity.Animal;
import dev.patika.spring.Entity.Appointment;
import dev.patika.spring.Entity.Doctor;
import dev.patika.spring.Repository.AnimalRepo;
import dev.patika.spring.Repository.AppointmentRepo;
import dev.patika.spring.Repository.AvailableDateRepo;
import dev.patika.spring.Repository.DoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private AppointmentRepo appointmentRepository;
    private DoctorRepo doctorRepository;
    private AvailableDateRepo availableDateRepository;
    private AnimalRepo animalRepo;
    private  AppointmentRepo appointmentRepo;
    @Autowired
    public AppointmentService(AppointmentRepo appointmentRepository, DoctorRepo doctorRepository, AvailableDateRepo availableDateRepository, AnimalRepo animalRepo, AppointmentRepo appointmentRepo) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.availableDateRepository = availableDateRepository;
        this.animalRepo = animalRepo;
        this.appointmentRepo = appointmentRepo;
    }

    // Randevu oluşturma

    // Randevu oluşturma
    public Appointment createAppointment(AppointmentRequest appointmentRequest) {
        LocalDateTime requestedDateTime = appointmentRequest.getAppointmentDate();
        if (appointmentRequest.getDoctor() == null ||
                appointmentRequest.getDoctor().getId() == null ||
                appointmentRequest.getAppointmentDate() == null ||
                appointmentRequest.getAnimal() == null ||
                appointmentRequest.getAnimal().getId() == null ) {
            throw new IllegalArgumentException("Randevuya ait alanlar boş olamaz.");
        }


        Long doctorId = appointmentRequest.getDoctor().getId();
        Long animalId = appointmentRequest.getAnimal().getId();
        LocalDate appointmentDate = requestedDateTime.toLocalDate();
        if (!doctorRepository.existsById(doctorId)) {
            throw new RuntimeException("Böyle bir doktor bulunmamaktadır!");

        } else {
            if (!doctorRepository.isDoctorAvailableOnDate(doctorId, appointmentDate)) {
                throw new RuntimeException("Doktor bu tarihte çalışmamaktadır!"); //DEĞERLENDİRME FORMU 22
            }
            if (appointmentRepository.existsByAppointmentDateAndDoctor_Id(requestedDateTime, doctorId)) {
                throw new RuntimeException("Girilen tarihte başka bir randevu mevcuttur."); //DEĞERLENDİRME FORMU 22
            }



        }
        if (requestedDateTime.getMinute() != 0 || requestedDateTime.getSecond() != 0) {
            throw new RuntimeException("Sadece saat başı randevu alınabilir.");
        }



        Appointment appointment = convertDtoToAppointment(appointmentRequest, animalId);
        appointment.setAppointmentDate(requestedDateTime); // Saat bilgisini atar

        return appointmentRepository.save(appointment);
    }
    private Appointment convertDtoToAppointment(AppointmentRequest appointmentRequest, Long animalId) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(appointmentRequest.getAppointmentDate());

        Long doctorId = appointmentRequest.getDoctor().getId();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doktor bulunamadı."));
        appointment.setDoctor(doctor);


        Animal animal = animalRepo.findById(animalId).orElseThrow(() -> new RuntimeException("Hayvan bulunamadı."));
        appointment.setAnimal(animal);


        return appointment;
    }

    public Appointment updateAppointment(long id, AppointmentRequest appointmentRequest) {
        // Veritabanından belirtilen id'ye sahip randevuyu bul
        Optional<Appointment> optionalAppointment = appointmentRepo.findById(id);
        if (appointmentRequest.getDoctor() == null ||
                appointmentRequest.getDoctor().getId() == null ||
                appointmentRequest.getAppointmentDate() == null ||
                appointmentRequest.getAnimal() == null ||
                appointmentRequest.getAnimal().getId() == null ) {
            throw new IllegalArgumentException("Randevuya ait alanlar boş olamaz.");
        }


        // Eğer randevu bulunamazsa, hata döndür
        if (!optionalAppointment.isPresent()) {
            throw new RuntimeException("Bu ID'de bir randevu bulunamadı.");
        }

        Appointment appointment = optionalAppointment.get();


        LocalDateTime requestedDateTime = appointmentRequest.getAppointmentDate();
        Long doctorId = appointmentRequest.getDoctor().getId();
        LocalDate appointmentDate = requestedDateTime.toLocalDate();
        if (!(appointment.getAppointmentDate().equals(appointmentRequest.getAppointmentDate()) || !(appointment.getDoctor().getId().equals(appointmentRequest.getDoctor().getId())))){
           if (appointmentRepo.existsByAppointmentDateAndDoctor_Id(requestedDateTime, doctorId)) {
                throw new RuntimeException("Girilen tarihte başka bir randevu mevcuttur.");
            }

        }
        if (!doctorRepository.existsById(doctorId)) {
            throw new RuntimeException("Böyle bir doktor bulunmamaktadır!");
        } else if (!doctorRepository.isDoctorAvailableOnDate(doctorId, appointmentDate)) {
            throw new RuntimeException("Doktor bu tarihte çalışmamaktadır!");
        }
        // Randevu tarihini ve diğer özelliklerini güncelle

        if (requestedDateTime.getMinute() != 0 || requestedDateTime.getSecond() != 0) {
            throw new RuntimeException("Sadece saat başı randevu alınabilir.");
        }



        // Güncellenmek istenen doktor ve hayvanı randevuya ata
        appointment.setDoctor(doctorRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doktor bulunamadı!")));
        appointment.setAnimal(animalRepo.findById(appointmentRequest.getAnimal().getId()).orElseThrow(() -> new RuntimeException("Hayvan bulunamadı!")));

        // Randevu tarihini güncelle
        appointment.setAppointmentDate(requestedDateTime);

        // Randevuyu güncelle (save metodu kullanılarak)
        return appointmentRepo.save(appointment);
    }

    // Appointment'ı AppointmentRequest'e dönüştürmek için metod
    private AppointmentRequest convertAppointmentToRequest(Appointment appointment, Doctor doctor, Animal animal) {
        AppointmentRequest appointmentRequest = new AppointmentRequest();
        appointmentRequest.setAppointmentDate(appointment.getAppointmentDate());
        appointmentRequest.setDoctor(doctor);
        appointmentRequest.setAnimal(animal);
        // Diğer özellikleri gerekiyorsa ayarla
        return appointmentRequest;
    }

    public boolean isDoctorExist(Long doctorId) {
        return doctorRepository.existsById(doctorId);
    }

    public boolean isAnimalExist(Long animalId) {
        return animalRepo.existsById(animalId);
    }







    public List<Appointment> findAppointmentsByDateAndAnimal(LocalDate startDate, LocalDate endDate, Long id) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        return appointmentRepository.findByAppointmentDateBetweenAndAnimal_Id(startDateTime, endDateTime, id);
    }






    // Randevu bilgilerini görüntüleme
    public Appointment getAppointment(Long appointmentId) {
        return appointmentRepository.findById(appointmentId).get();
    }

    // Randevuları tarih aralığına ve doktora göre filtreleme
    public List<Appointment> findAppointmentsByDateAndDoctor(LocalDate startDate, LocalDate endDate, Long id) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        return appointmentRepository.findByAppointmentDateBetweenAndDoctor_Id(startDateTime, endDateTime, id);
    }


    // Randevuların tamamını getirme
    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }


}