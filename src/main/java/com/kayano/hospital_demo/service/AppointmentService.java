package com.kayano.hospital_demo.service;

import com.kayano.hospital_demo.entity.Appointment;

import java.util.List;

/**
 * 预约服务接口
 */
public interface AppointmentService {
    
    /**
     * 创建预约
     */
    Appointment createAppointment(Appointment appointment);
    
    /**
     * 根据ID查询预约
     */
    Appointment getAppointmentById(Long id);
    
    /**
     * 查询所有预约
     */
    List<Appointment> getAllAppointments();
    
    /**
     * 根据患者ID查询预约
     */
    List<Appointment> getAppointmentsByPatientId(Long patientId);
    
    /**
     * 根据医生ID查询预约
     */
    List<Appointment> getAppointmentsByDoctorId(Long doctorId);
    
    /**
     * 更新预约状态
     */
    boolean updateAppointmentStatus(Long id, String status);
    
    /**
     * 更新预约信息
     */
    Appointment updateAppointment(Appointment appointment);
    
    /**
     * 取消预约
     */
    boolean cancelAppointment(Long id);
    
    /**
     * 删除预约
     */
    boolean deleteAppointment(Long id);
} 