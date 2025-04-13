package com.kayano.hospital_demo.service.impl;

import com.kayano.hospital_demo.entity.Appointment;
import com.kayano.hospital_demo.mapper.AppointmentMapper;
import com.kayano.hospital_demo.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 预约服务实现类
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;
    
    @Override
    public Appointment createAppointment(Appointment appointment) {
        // 如果没有设置状态，默认为"等待中"
        if (appointment.getStatus() == null || appointment.getStatus().isEmpty()) {
            appointment.setStatus("等待中");
        }
        appointmentMapper.insert(appointment);
        return appointment;
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentMapper.findById(id);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentMapper.findAll();
    }

    @Override
    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentMapper.findByPatientId(patientId);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentMapper.findByDoctorId(doctorId);
    }

    @Override
    public boolean updateAppointmentStatus(Long id, String status) {
        return appointmentMapper.updateStatus(id, status) > 0;
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        appointmentMapper.update(appointment);
        return appointmentMapper.findById(appointment.getId());
    }

    @Override
    public boolean cancelAppointment(Long id) {
        return appointmentMapper.updateStatus(id, "已取消") > 0;
    }
} 