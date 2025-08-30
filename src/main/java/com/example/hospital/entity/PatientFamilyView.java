package com.example.hospital.entity;

import java.time.LocalDateTime;

/**
 * 患者家属视图实体类
 * 对应数据库中的patient_family_view视图
 */
public class PatientFamilyView {
    
    // 紧急联系人
    private String emergencyContact;
    
    // 患者编号
    private String patientId;
    
    // 患者姓名
    private String patientName;
    
    // 诊断结果
    private String diagnosis;
    
    // 性别
    private String gender;
    
    // 年龄
    private Integer age;
    
    // 联系电话
    private String phoneNumber;
    
    // 地址
    private String address;
    
    // 患者状态
    private String patientStatus;
    
    // 出院时间
    private LocalDateTime dischargeTime;

    // Getters and Setters
    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(String patientStatus) {
        this.patientStatus = patientStatus;
    }

    public LocalDateTime getDischargeTime() {
        return dischargeTime;
    }

    public void setDischargeTime(LocalDateTime dischargeTime) {
        this.dischargeTime = dischargeTime;
    }
} 