package com.kayano.hospital_demo.mapper;

import com.kayano.hospital_demo.entity.Doctor;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 医生数据访问接口
 */
@Mapper
public interface DoctorMapper {
    
    /**
     * 新增医生
     */
    @Insert("INSERT INTO doctor(name, gender, department, position, specialty, phone) " +
            "VALUES(#{name}, #{gender}, #{department}, #{position}, #{specialty}, #{phone})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Doctor doctor);
    
    /**
     * 根据ID查询医生
     */
    @Select("SELECT * FROM doctor WHERE id = #{id}")
    Doctor findById(Long id);
    
    /**
     * 查询所有医生
     */
    @Select("SELECT * FROM doctor ORDER BY id DESC")
    List<Doctor> findAll();
    
    /**
     * 根据科室查询医生
     */
    @Select("SELECT * FROM doctor WHERE department = #{department} ORDER BY id DESC")
    List<Doctor> findByDepartment(String department);
    
    /**
     * 根据姓名模糊查询医生
     */
    @Select("SELECT * FROM doctor WHERE name LIKE CONCAT('%', #{name}, '%') ORDER BY id DESC")
    List<Doctor> findByName(String name);
    
    /**
     * 更新医生信息
     */
    @Update("UPDATE doctor SET name = #{name}, gender = #{gender}, department = #{department}, " +
            "position = #{position}, specialty = #{specialty}, phone = #{phone} WHERE id = #{id}")
    int update(Doctor doctor);
    
    /**
     * 删除医生
     */
    @Delete("DELETE FROM doctor WHERE id = #{id}")
    int deleteById(Long id);
} 