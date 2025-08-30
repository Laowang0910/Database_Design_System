package com.example.hospital.mapper;

import com.example.hospital.entity.PatientFamilyView;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 患者家属视图数据访问层接口
 */
@Mapper
public interface PatientFamilyViewMapper {
    
    /**
     * 获取所有患者家属视图信息
     */
    @Select("SELECT * FROM patient_family_view")
    List<PatientFamilyView> getAllPatientFamilyViews();

    /**
     * 根据患者ID查询家属视图信息
     */
    @Select("SELECT * FROM patient_family_view WHERE patient_id = #{patientId}")
    PatientFamilyView getPatientFamilyViewById(String patientId);

    /**
     * 根据患者姓名模糊查询家属视图信息
     */
    @Select("SELECT * FROM patient_family_view WHERE patient_name LIKE CONCAT('%', #{patientName}, '%')")
    List<PatientFamilyView> getPatientFamilyViewsByName(String patientName);
} 