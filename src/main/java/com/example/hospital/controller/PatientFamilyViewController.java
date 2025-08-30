package com.example.hospital.controller;

import com.example.hospital.entity.PatientFamilyView;
import com.example.hospital.service.PatientFamilyViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 患者家属视图控制器
 */
@RestController
@RequestMapping("/api/patient-family-views")
public class PatientFamilyViewController {
    
    @Autowired
    private PatientFamilyViewService patientFamilyViewService;

    /**
     * 获取所有患者家属视图信息
     */
    @GetMapping
    public List<PatientFamilyView> getAllPatientFamilyViews() {
        return patientFamilyViewService.getAllPatientFamilyViews();
    }

    /**
     * 根据患者ID查询家属视图信息
     */
    @GetMapping("/{patientId}")
    public PatientFamilyView getPatientFamilyViewById(@PathVariable String patientId) {
        return patientFamilyViewService.getPatientFamilyViewById(patientId);
    }

    /**
     * 根据患者姓名模糊查询家属视图信息
     */
    @GetMapping("/search")
    public List<PatientFamilyView> getPatientFamilyViewsByName(@RequestParam String patientName) {
        return patientFamilyViewService.getPatientFamilyViewsByName(patientName);
    }
} 