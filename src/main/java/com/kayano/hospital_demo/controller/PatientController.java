package com.kayano.hospital_demo.controller;

import com.kayano.hospital_demo.entity.Patient;
import com.kayano.hospital_demo.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 患者相关接口
 */
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;
    
    /**
     * 添加患者
     */
    @PostMapping("")
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        Patient newPatient = patientService.addPatient(patient);
        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }
    
    /**
     * 根据ID查询患者
     */
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        if (patient != null) {
            return new ResponseEntity<>(patient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * 查询所有患者
     */
    @GetMapping("")
    public ResponseEntity<List<Patient>> getAllPatients() {
        return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK);
    }
    
    /**
     * 根据姓名查询患者
     */
    @GetMapping("/search")
    public ResponseEntity<List<Patient>> getPatientsByName(@RequestParam String name) {
        return new ResponseEntity<>(patientService.getPatientsByName(name), HttpStatus.OK);
    }
    
    /**
     * 更新患者信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        Patient existingPatient = patientService.getPatientById(id);
        if (existingPatient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        patient.setId(id);
        Patient updatedPatient = patientService.updatePatient(patient);
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }
    
    /**
     * 删除患者
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        Patient existingPatient = patientService.getPatientById(id);
        if (existingPatient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        if (patientService.deletePatient(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 