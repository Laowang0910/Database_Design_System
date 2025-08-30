package com.example.hospital.entity;

import java.time.LocalDateTime;

/**
 * 患者护理记录实体类
 * 对应数据库中的患者护理记录表
 */
public class PatientCare {
    
    // 护理记录编号
    private String careId;
    
    // 患者编号
    private String patientId;
    
    // 护理人员编号
    private String staffId;
    
    // 护理时间
    private LocalDateTime careTime;
    
    // 护理类型（常规护理/特殊护理）
    private String careType;
    
    // 体温
    private Double temperature;
    
    // 脉搏
    private Integer pulse;
    
    // 血压
    private String bloodPressure;
    
    // 呼吸频率
    private Integer respiratoryRate;
    
    // 护理内容
    private String careContent;
    
    // 患者反应
    private String patientResponse;
    
    // 备注
    private String remarks;
    
    // 创建时间
    private LocalDateTime createTime;
    
    // 更新时间
    private LocalDateTime updateTime;

    // Getters and Setters
    public String getCareId() {
        return careId;
    }

    public void setCareId(String careId) {
        this.careId = careId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public LocalDateTime getCareTime() {
        return careTime;
    }

    public void setCareTime(LocalDateTime careTime) {
        this.careTime = careTime;
    }

    public String getCareType() {
        return careType;
    }

    public void setCareType(String careType) {
        this.careType = careType;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getPulse() {
        return pulse;
    }

    public void setPulse(Integer pulse) {
        this.pulse = pulse;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public Integer getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(Integer respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public String getCareContent() {
        return careContent;
    }

    public void setCareContent(String careContent) {
        this.careContent = careContent;
    }

    public String getPatientResponse() {
        return patientResponse;
    }

    public void setPatientResponse(String patientResponse) {
        this.patientResponse = patientResponse;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
} 