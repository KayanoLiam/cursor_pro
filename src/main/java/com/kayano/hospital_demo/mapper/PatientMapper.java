package com.kayano.hospital_demo.mapper;

import com.kayano.hospital_demo.entity.Patient;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 患者数据访问接口
 */
@Mapper
public interface PatientMapper {
    
    /**
     * 新增患者
     */
    @Insert("INSERT INTO patient(name, gender, age, phone, address, medical_history) " +
            "VALUES(#{name}, #{gender}, #{age}, #{phone}, #{address}, #{medicalHistory})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Patient patient);
    
    /**
     * 根据ID查询患者
     */
    @Select("SELECT * FROM patient WHERE id = #{id}")
    Patient findById(Long id);
    
    /**
     * 查询所有患者
     */
    @Select("SELECT * FROM patient ORDER BY id DESC")
    List<Patient> findAll();
    
    /**
     * 根据姓名模糊查询患者
     */
    @Select("SELECT * FROM patient WHERE name LIKE CONCAT('%', #{name}, '%') ORDER BY id DESC")
    List<Patient> findByName(String name);
    
    /**
     * 更新患者信息
     */
    @Update("UPDATE patient SET name = #{name}, gender = #{gender}, age = #{age}, " +
            "phone = #{phone}, address = #{address}, medical_history = #{medicalHistory} WHERE id = #{id}")
    int update(Patient patient);
    
    /**
     * 删除患者
     */
    @Delete("DELETE FROM patient WHERE id = #{id}")
    int deleteById(Long id);
} 