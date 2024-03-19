package dev.patika.spring.Service;


import dev.patika.spring.Dto.Request.ReportRequest;
import dev.patika.spring.Entity.Appointment;
import dev.patika.spring.Entity.Report;
import dev.patika.spring.Repository.AppointmentRepo;
import dev.patika.spring.Repository.ReportRepo;

import dev.patika.spring.Repository.VaccineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReportService {
    private final VaccineRepo vaccineRepo;
    private final ReportRepo reportRepo;
    private final AppointmentRepo appointmentRepo;


    @Autowired
    public ReportService(ReportRepo reportRepo, VaccineRepo vaccineRepo, AppointmentRepo appointmentRepo) {

        this.vaccineRepo = vaccineRepo;
        this.reportRepo = reportRepo;
        this.appointmentRepo = appointmentRepo;
    }



    public boolean isAppointmentExist(Long appointmentId) {
        return appointmentRepo.existsById(appointmentId);
    }




    private Report convertToReport(ReportRequest reportRequest) {
        Report report = new Report();
        report.setTitle(reportRequest.getTitle());
        report.setDiagnosis(reportRequest.getDiagnosis());
        report.setPrice(reportRequest.getPrice());

        // AnimalRequest içinde bir Customer nesnesi varsa ve içinde bir ID varsa,
        // burada sadece ID'yi alıp set etmeniz gerekebilir. Örneğin:
        if (reportRequest.getAppointment() == null) {
            throw new IllegalArgumentException("Randevu bilgisi eksik.");
        }
        if (reportRequest.getAppointment() != null && reportRequest.getAppointment().getId() != null) {
            Appointment appointment = new Appointment();
            appointment.setId(reportRequest.getAppointment().getId());
            report.setAppointment(appointment);
        }

        return report;
    }

}
