package com.example.hospital.mapper;

import com.example.hospital.entity.Prescription;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface PrescriptionMapper {
    
    /**
     * 获取所有处方
     */
    @Select("SELECT * FROM prescriptions ORDER BY start_date DESC")
    @Results({
        @Result(property = "prescriptionId", column = "prescription_id"),
        @Result(property = "patientId", column = "patient_id"),
        @Result(property = "doctorId", column = "doctor_id"),
        @Result(property = "medicationName", column = "medication_name"),
        @Result(property = "dosage", column = "dosage"),
        @Result(property = "frequency", column = "frequency"),
        @Result(property = "startDate", column = "start_date"),
        @Result(property = "endDate", column = "end_date"),
        @Result(property = "instruction", column = "notes"),
        @Result(property = "status", column = "status")
    })
    List<Prescription> getAllPrescriptions();

    /**
     * 根据患者ID获取处方
     */
    @Select("SELECT * FROM prescriptions WHERE patient_id = #{patientId} ORDER BY start_date DESC")
    @Results({
        @Result(property = "prescriptionId", column = "prescription_id"),
        @Result(property = "patientId", column = "patient_id"),
        @Result(property = "doctorId", column = "doctor_id"),
        @Result(property = "medicationName", column = "medication_name"),
        @Result(property = "dosage", column = "dosage"),
        @Result(property = "frequency", column = "frequency"),
        @Result(property = "startDate", column = "start_date"),
        @Result(property = "endDate", column = "end_date"),
        @Result(property = "instruction", column = "notes"),
        @Result(property = "status", column = "status")
    })
    List<Prescription> getPrescriptionsByPatientId(String patientId);

    /**
     * 根据处方ID获取处方
     */
    @Select("SELECT * FROM prescriptions WHERE prescription_id = #{prescriptionId}")
    @Results({
        @Result(property = "prescriptionId", column = "prescription_id"),
        @Result(property = "patientId", column = "patient_id"),
        @Result(property = "doctorId", column = "doctor_id"),
        @Result(property = "medicationName", column = "medication_name"),
        @Result(property = "dosage", column = "dosage"),
        @Result(property = "frequency", column = "frequency"),
        @Result(property = "startDate", column = "start_date"),
        @Result(property = "endDate", column = "end_date"),
        @Result(property = "instruction", column = "notes"),
        @Result(property = "status", column = "status")
    })
    Prescription getPrescriptionById(String prescriptionId);

    /**
     * 更新处方状态
     */
    @Update("UPDATE prescriptions SET status = #{status} WHERE prescription_id = #{prescriptionId}")
    int updatePrescriptionStatus(@Param("prescriptionId") String prescriptionId, @Param("status") String status);

    /**
     * 更新处方信息
     */
    @Update("UPDATE prescriptions SET medication_name=#{medicationName}, dosage=#{dosage}, frequency=#{frequency}, notes=#{instruction} WHERE prescription_id=#{prescriptionId}")
    int updatePrescription(Prescription prescription);
} 