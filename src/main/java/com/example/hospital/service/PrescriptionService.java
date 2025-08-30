package com.example.hospital.service;

import com.example.hospital.entity.Prescription;
import com.example.hospital.mapper.PrescriptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {
    
    @Autowired
    private PrescriptionMapper prescriptionMapper;
    
    /**
     * 获取所有医嘱
     */
    public List<Prescription> getAllPrescriptions() {
        return prescriptionMapper.getAllPrescriptions();
    }
    
    /**
     * 根据患者ID获取医嘱
     */
    public List<Prescription> getPrescriptionsByPatientId(String patientId) {
        return prescriptionMapper.getPrescriptionsByPatientId(patientId);
    }
    
    /**
     * 根据医嘱ID获取医嘱
     */
    public Prescription getPrescriptionById(String prescriptionId) {
        return prescriptionMapper.getPrescriptionById(prescriptionId);
    }

    /**
     * 执行医嘱
     */
    public boolean executePrescription(String prescriptionId) {
        Prescription prescription = getPrescriptionById(prescriptionId);
        if (prescription == null) {
            return false;
        }
        
        int rows = prescriptionMapper.updatePrescriptionStatus(prescriptionId, "已执行");
        return rows > 0;
    }

    /**
     * 更新处方信息
     */
    public boolean updatePrescription(Prescription prescription) {
        return prescriptionMapper.updatePrescription(prescription) > 0;
    }
} 