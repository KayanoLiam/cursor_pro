package com.kayano.hospital_demo.controller;

import com.kayano.hospital_demo.entity.Doctor;
import com.kayano.hospital_demo.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 医生相关接口
 */
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    
    /**
     * 添加医生
     */
    @PostMapping("")
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor) {
        Doctor newDoctor = doctorService.addDoctor(doctor);
        return new ResponseEntity<>(newDoctor, HttpStatus.CREATED);
    }
    
    /**
     * 根据ID查询医生
     */
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor != null) {
            return new ResponseEntity<>(doctor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * 查询所有医生
     */
    @GetMapping("")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return new ResponseEntity<>(doctorService.getAllDoctors(), HttpStatus.OK);
    }
    
    /**
     * 根据科室查询医生
     */
    @GetMapping("/department/{department}")
    public ResponseEntity<List<Doctor>> getDoctorsByDepartment(@PathVariable String department) {
        return new ResponseEntity<>(doctorService.getDoctorsByDepartment(department), HttpStatus.OK);
    }
    
    /**
     * 根据姓名查询医生
     */
    @GetMapping("/search")
    public ResponseEntity<List<Doctor>> getDoctorsByName(@RequestParam String name) {
        return new ResponseEntity<>(doctorService.getDoctorsByName(name), HttpStatus.OK);
    }
    
    /**
     * 更新医生信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        Doctor existingDoctor = doctorService.getDoctorById(id);
        if (existingDoctor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        doctor.setId(id);
        Doctor updatedDoctor = doctorService.updateDoctor(doctor);
        return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
    }
    
    /**
     * 删除医生
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        Doctor existingDoctor = doctorService.getDoctorById(id);
        if (existingDoctor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        if (doctorService.deleteDoctor(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 