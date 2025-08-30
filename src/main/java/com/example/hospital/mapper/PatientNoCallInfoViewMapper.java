package com.example.hospital.mapper;

import com.example.hospital.entity.PatientNoCallInfoView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PatientNoCallInfoViewMapper {
    @Select("SELECT * FROM patient_nocall_info_view WHERE patient_id = #{patientId}")
    List<PatientNoCallInfoView> getNoCallPatientsByPatientId(@Param("patientId") String patientId);
} 