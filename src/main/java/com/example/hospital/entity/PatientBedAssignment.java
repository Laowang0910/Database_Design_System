package com.example.hospital.entity;

import java.time.LocalDateTime;

/**
 * 患者床位分配实体类
 * 对应数据库中的患者床位信息表
 */
public class PatientBedAssignment {
    
    // 分配编号
    private String assignId;
    
    // 患者编号
    private String patientId;
    
    // 床位编号
    private String bedId;
    
    // 分配时间
    private LocalDateTime assignTime;
    
    // 退床时间
    private LocalDateTime dischargeTime;
    
    // 分配状态（有效/已结束）
    private String assignStatus;
    
    // 创建时间
    private LocalDateTime createTime;
    
    // 更新时间
    private LocalDateTime updateTime;

    // Getters and Setters
    public String getAssignId() {
        return assignId;
    }

    public void setAssignId(String assignId) {
        this.assignId = assignId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public LocalDateTime getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(LocalDateTime assignTime) {
        this.assignTime = assignTime;
    }

    public LocalDateTime getDischargeTime() {
        return dischargeTime;
    }

    public void setDischargeTime(LocalDateTime dischargeTime) {
        this.dischargeTime = dischargeTime;
    }

    public String getAssignStatus() {
        return assignStatus;
    }

    public void setAssignStatus(String assignStatus) {
        this.assignStatus = assignStatus;
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