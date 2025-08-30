package com.example.hospital.mapper;

import com.example.hospital.entity.PatientCallInfoView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface PatientCallInfoViewMapper {
    @Select("SELECT v.*, p.call_status FROM patient_call_info_view v JOIN 患者信息表 p ON v.patient_id = p.patient_id WHERE p.call_status = '呼叫'")
    List<PatientCallInfoView> getAllCallingPatients();
} 