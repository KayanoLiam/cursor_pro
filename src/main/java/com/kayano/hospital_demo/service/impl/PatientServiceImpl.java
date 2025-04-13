package com.kayano.hospital_demo.service.impl;

import com.kayano.hospital_demo.entity.Patient;
import com.kayano.hospital_demo.mapper.AppointmentMapper;
import com.kayano.hospital_demo.mapper.PatientMapper;
import com.kayano.hospital_demo.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 患者服务实现类
 */
@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientMapper patientMapper;
    
    @Autowired
    private AppointmentMapper appointmentMapper;
    
    @Override
    public Patient addPatient(Patient patient) {
        patientMapper.insert(patient);
        return patient;
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientMapper.findById(id);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientMapper.findAll();
    }

    @Override
    public List<Patient> getPatientsByName(String name) {
        return patientMapper.findByName(name);
    }

    @Override
    public Patient updatePatient(Patient patient) {
        patientMapper.update(patient);
        return patient;
    }

    @Override
    @Transactional
    public boolean deletePatient(Long id) {
        // 先删除所有与该患者关联的预约记录
        appointmentMapper.deleteByPatientId(id);
        
        // 然后删除患者
        return patientMapper.deleteById(id) > 0;
    }
} 