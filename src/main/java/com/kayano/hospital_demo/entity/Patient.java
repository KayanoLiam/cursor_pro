package com.kayano.hospital_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 患者实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    private Long id;           // 患者ID
    private String name;       // 姓名
    private String gender;     // 性别
    private Integer age;       // 年龄
    private String phone;      // 电话
    private String address;    // 地址
    private String medicalHistory; // 病史
    
    // 手动添加所有的getter和setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getMedicalHistory() {
        return medicalHistory;
    }
    
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
} 