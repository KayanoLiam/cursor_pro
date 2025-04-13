package com.kayano.hospital_demo.service;

import com.kayano.hospital_demo.entity.Doctor;

import java.util.List;

/**
 * 医生服务接口
 */
public interface DoctorService {
    
    /**
     * 添加医生
     */
    Doctor addDoctor(Doctor doctor);
    
    /**
     * 根据ID查询医生
     */
    Doctor getDoctorById(Long id);
    
    /**
     * 查询所有医生
     */
    List<Doctor> getAllDoctors();
    
    /**
     * 根据科室查询医生
     */
    List<Doctor> getDoctorsByDepartment(String department);
    
    /**
     * 根据姓名查询医生
     */
    List<Doctor> getDoctorsByName(String name);
    
    /**
     * 更新医生信息
     */
    Doctor updateDoctor(Doctor doctor);
    
    /**
     * 删除医生
     */
    boolean deleteDoctor(Long id);
} 