package com.kayano.hospital_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 预约记录实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    private Long id;                 // 预约ID
    private Long patientId;          // 患者ID
    private Long doctorId;           // 医生ID
    private LocalDateTime appointmentTime; // 预约时间
    private String status;           // 预约状态：等待中、已完成、已取消
    private String description;      // 预约描述/症状
    
    // 非数据库字段，用于显示关联信息
    private String patientName;      // 患者姓名
    private String doctorName;       // 医生姓名
    private String doctorDepartment; // 医生科室
    
    // 手动添加所有的getter和setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getPatientId() {
        return patientId;
    }
    
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
    
    public Long getDoctorId() {
        return doctorId;
    }
    
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
    
    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }
    
    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getPatientName() {
        return patientName;
    }
    
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    
    public String getDoctorName() {
        return doctorName;
    }
    
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    
    public String getDoctorDepartment() {
        return doctorDepartment;
    }
    
    public void setDoctorDepartment(String doctorDepartment) {
        this.doctorDepartment = doctorDepartment;
    }
} 