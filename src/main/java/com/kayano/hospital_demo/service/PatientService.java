package com.kayano.hospital_demo.service;

import com.kayano.hospital_demo.entity.Patient;

import java.util.List;

/**
 * 患者服务接口
 */
public interface PatientService {
    
    /**
     * 添加患者
     */
    Patient addPatient(Patient patient);
    
    /**
     * 根据ID查询患者
     */
    Patient getPatientById(Long id);
    
    /**
     * 查询所有患者
     */
    List<Patient> getAllPatients();
    
    /**
     * 根据姓名查询患者
     */
    List<Patient> getPatientsByName(String name);
    
    /**
     * 更新患者信息
     */
    Patient updatePatient(Patient patient);
    
    /**
     * 删除患者
     */
    boolean deletePatient(Long id);
} 