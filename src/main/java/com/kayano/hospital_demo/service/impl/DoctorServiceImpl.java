package com.kayano.hospital_demo.service.impl;

import com.kayano.hospital_demo.entity.Doctor;
import com.kayano.hospital_demo.mapper.DoctorMapper;
import com.kayano.hospital_demo.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 医生服务实现类
 */
@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;
    
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
    public boolean deleteDoctor(Long id) {
        return doctorMapper.deleteById(id) > 0;
    }
} 