package com.example.hospital.mapper;

import com.example.hospital.entity.PatientBedInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PatientBedInfoMapper {

    @Insert("INSERT INTO `患者床位信息表` (patient_id, bed_id, assign_time, assign_status, cerate_time, update_time) " +
            "VALUES (#{patientId}, #{bedId}, #{assignTime}, #{assignStatus}, #{cerateTime}, #{updateTime})")
    void insertPatientBedInfo(PatientBedInfo patientBedInfo);

    @Update("UPDATE `患者床位信息表` SET assign_status = '无效' WHERE patient_id = #{patientId} AND bed_id = #{bedId}")
    void invalidatePatientBedInfo(@Param("patientId") String patientId, @Param("bedId") String bedId);

    @Delete("DELETE FROM `患者床位信息表` WHERE patient_id = #{patientId} AND bed_id = #{bedId}")
    void deletePatientBedInfo(@Param("patientId") String patientId, @Param("bedId") String bedId);

    @Select("SELECT bed_id FROM `患者床位信息表` WHERE patient_id = #{patientId} AND assign_status = '有效' LIMIT 1")
    String findCurrentBedIdByPatientId(@Param("patientId") String patientId);
} 