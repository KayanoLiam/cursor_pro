package com.kayano.hospital_demo.mapper;

import com.kayano.hospital_demo.entity.Appointment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 预约记录数据访问接口
 */
@Mapper
public interface AppointmentMapper {
    
    /**
     * 创建预约
     */
    @Insert("INSERT INTO appointment(patient_id, doctor_id, appointment_time, status, description) " +
            "VALUES(#{patientId}, #{doctorId}, #{appointmentTime}, #{status}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Appointment appointment);
    
    /**
     * 根据ID查询预约
     */
    @Select("SELECT a.*, p.name as patient_name, d.name as doctor_name, d.department as doctor_department " +
            "FROM appointment a " +
            "LEFT JOIN patient p ON a.patient_id = p.id " +
            "LEFT JOIN doctor d ON a.doctor_id = d.id " +
            "WHERE a.id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "patientId", column = "patient_id"),
            @Result(property = "doctorId", column = "doctor_id"),
            @Result(property = "appointmentTime", column = "appointment_time"),
            @Result(property = "status", column = "status"),
            @Result(property = "description", column = "description"),
            @Result(property = "patientName", column = "patient_name"),
            @Result(property = "doctorName", column = "doctor_name"),
            @Result(property = "doctorDepartment", column = "doctor_department")
    })
    Appointment findById(Long id);
    
    /**
     * 查询所有预约
     */
    @Select("SELECT a.*, p.name as patient_name, d.name as doctor_name, d.department as doctor_department " +
            "FROM appointment a " +
            "LEFT JOIN patient p ON a.patient_id = p.id " +
            "LEFT JOIN doctor d ON a.doctor_id = d.id " +
            "ORDER BY a.appointment_time DESC")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "patientId", column = "patient_id"),
            @Result(property = "doctorId", column = "doctor_id"),
            @Result(property = "appointmentTime", column = "appointment_time"),
            @Result(property = "status", column = "status"),
            @Result(property = "description", column = "description"),
            @Result(property = "patientName", column = "patient_name"),
            @Result(property = "doctorName", column = "doctor_name"),
            @Result(property = "doctorDepartment", column = "doctor_department")
    })
    List<Appointment> findAll();
    
    /**
     * 根据患者ID查询预约
     */
    @Select("SELECT a.*, p.name as patient_name, d.name as doctor_name, d.department as doctor_department " +
            "FROM appointment a " +
            "LEFT JOIN patient p ON a.patient_id = p.id " +
            "LEFT JOIN doctor d ON a.doctor_id = d.id " +
            "WHERE a.patient_id = #{patientId} " +
            "ORDER BY a.appointment_time DESC")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "patientId", column = "patient_id"),
            @Result(property = "doctorId", column = "doctor_id"),
            @Result(property = "appointmentTime", column = "appointment_time"),
            @Result(property = "status", column = "status"),
            @Result(property = "description", column = "description"),
            @Result(property = "patientName", column = "patient_name"),
            @Result(property = "doctorName", column = "doctor_name"),
            @Result(property = "doctorDepartment", column = "doctor_department")
    })
    List<Appointment> findByPatientId(Long patientId);
    
    /**
     * 根据医生ID查询预约
     */
    @Select("SELECT a.*, p.name as patient_name, d.name as doctor_name, d.department as doctor_department " +
            "FROM appointment a " +
            "LEFT JOIN patient p ON a.patient_id = p.id " +
            "LEFT JOIN doctor d ON a.doctor_id = d.id " +
            "WHERE a.doctor_id = #{doctorId} " +
            "ORDER BY a.appointment_time DESC")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "patientId", column = "patient_id"),
            @Result(property = "doctorId", column = "doctor_id"),
            @Result(property = "appointmentTime", column = "appointment_time"),
            @Result(property = "status", column = "status"),
            @Result(property = "description", column = "description"),
            @Result(property = "patientName", column = "patient_name"),
            @Result(property = "doctorName", column = "doctor_name"),
            @Result(property = "doctorDepartment", column = "doctor_department")
    })
    List<Appointment> findByDoctorId(Long doctorId);
    
    /**
     * 更新预约状态
     */
    @Update("UPDATE appointment SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
    
    /**
     * 更新预约信息
     */
    @Update("UPDATE appointment SET patient_id = #{patientId}, doctor_id = #{doctorId}, " +
            "appointment_time = #{appointmentTime}, status = #{status}, description = #{description} " +
            "WHERE id = #{id}")
    int update(Appointment appointment);
    
    /**
     * 删除预约
     */
    @Delete("DELETE FROM appointment WHERE id = #{id}")
    int deleteById(Long id);
} 