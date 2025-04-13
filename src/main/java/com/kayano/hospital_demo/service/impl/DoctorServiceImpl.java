package com.kayano.hospital_demo.service.impl;

import com.kayano.hospital_demo.entity.Doctor;
import com.kayano.hospital_demo.mapper.AppointmentMapper;
import com.kayano.hospital_demo.mapper.DoctorMapper;
import com.kayano.hospital_demo.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 医生服务实现类
 */
@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;
    
    @Autowired
    private AppointmentMapper appointmentMapper;
    
    @Override
    public Doctor addDoctor(Doctor doctor) {
        doctorMapper.insert(doctor);
        return doctor;
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return doctorMapper.findById(id);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorMapper.findAll();
    }

    @Override
    public List<Doctor> getDoctorsByDepartment(String department) {
        return doctorMapper.findByDepartment(department);
    }

    @Override
    public List<Doctor> getDoctorsByName(String name) {
        return doctorMapper.findByName(name);
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) {
        doctorMapper.update(doctor);
        return doctor;
    }

    @Override
    @Transactional
    public boolean deleteDoctor(Long id) {
        // 先删除所有与该医生关联的预约记录
        appointmentMapper.deleteByDoctorId(id);
        
        // 然后删除医生
        return doctorMapper.deleteById(id) > 0;
    }
} 