package com.kayano.hospital_demo.controller;

import com.kayano.hospital_demo.entity.Appointment;
import com.kayano.hospital_demo.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 预约相关接口
 */
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    
    /**
     * 创建预约
     */
    @PostMapping("")
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment newAppointment = appointmentService.createAppointment(appointment);
        return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
    }
    
    /**
     * 根据ID查询预约
     */
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        if (appointment != null) {
            return new ResponseEntity<>(appointment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * 查询所有预约
     */
    @GetMapping("")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return new ResponseEntity<>(appointmentService.getAllAppointments(), HttpStatus.OK);
    }
    
    /**
     * 根据患者ID查询预约
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatientId(@PathVariable Long patientId) {
        return new ResponseEntity<>(appointmentService.getAppointmentsByPatientId(patientId), HttpStatus.OK);
    }
    
    /**
     * 根据医生ID查询预约
     */
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctorId(@PathVariable Long doctorId) {
        return new ResponseEntity<>(appointmentService.getAppointmentsByDoctorId(doctorId), HttpStatus.OK);
    }
    
    /**
     * 更新预约状态
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateAppointmentStatus(@PathVariable Long id, @RequestParam String status) {
        if (appointmentService.updateAppointmentStatus(id, status)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * 更新预约信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        Appointment existingAppointment = appointmentService.getAppointmentById(id);
        if (existingAppointment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        appointment.setId(id);
        Appointment updatedAppointment = appointmentService.updateAppointment(appointment);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }
    
    /**
     * 取消预约
     */
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
        if (appointmentService.cancelAppointment(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
} 