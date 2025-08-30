package com.example.hospital.service;

import com.example.hospital.entity.PatientFamilyView;
import com.example.hospital.mapper.PatientFamilyViewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 患者家属视图服务层
 */
@Service
public class PatientFamilyViewService {
    
    @Autowired
    private PatientFamilyViewMapper patientFamilyViewMapper;

    /**
     * 获取所有患者家属视图信息
     */
    public List<PatientFamilyView> getAllPatientFamilyViews() {
        return patientFamilyViewMapper.getAllPatientFamilyViews();
    }

    /**
     * 根据患者ID查询家属视图信息
     */
    public PatientFamilyView getPatientFamilyViewById(String patientId) {
        return patientFamilyViewMapper.getPatientFamilyViewById(patientId);
    }

    /**
     * 根据患者姓名模糊查询家属视图信息
     */
    public List<PatientFamilyView> getPatientFamilyViewsByName(String patientName) {
        return patientFamilyViewMapper.getPatientFamilyViewsByName(patientName);
    }
} 