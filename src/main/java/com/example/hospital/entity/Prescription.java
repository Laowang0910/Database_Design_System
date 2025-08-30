package com.example.hospital.entity;

import java.time.LocalDate;

/**
 * 医嘱实体类
 */
public class Prescription {
    // 处方ID
    private String prescriptionId;
    
    // 患者ID
    private String patientId;
    
    // 医生ID
    private String doctorId;
    
    // 药品名称
    private String medicationName;
    
    // 剂量
    private String dosage;
    
    // 频率
    private String frequency;
    
    // 开始日期
    private LocalDate startDate;
    
    // 结束日期
    private LocalDate endDate;
    
    // 医嘱
    private String instruction;
    
    // 状态（未执行/已执行）
    private String status;

    // Getters and Setters
    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
} 