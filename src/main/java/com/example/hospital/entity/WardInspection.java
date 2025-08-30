package com.example.hospital.entity;

import java.time.LocalDateTime;

/**
 * 病房巡查记录实体类
 * 对应数据库中的病房巡查记录表
 */
public class WardInspection {
    
    // 巡查记录编号
    private String inspectionId;
    
    // 病房编号
    private String wardId;
    
    // 巡查人员编号
    private String staffId;
    
    // 巡查时间
    private LocalDateTime inspectionTime;
    
    // 病房状况
    private String wardCondition;
    
    // 设备状况
    private String deviceCondition;
    
    // 患者状况
    private String patientCondition;
    
    // 异常情况
    private String abnormalSituation;
    
    // 处理措施
    private String measures;
    
    // 备注
    private String remarks;
    
    // 创建时间
    private LocalDateTime createTime;
    
    // 更新时间
    private LocalDateTime updateTime;

    // Getters and Setters
    public String getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public LocalDateTime getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(LocalDateTime inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getWardCondition() {
        return wardCondition;
    }

    public void setWardCondition(String wardCondition) {
        this.wardCondition = wardCondition;
    }

    public String getDeviceCondition() {
        return deviceCondition;
    }

    public void setDeviceCondition(String deviceCondition) {
        this.deviceCondition = deviceCondition;
    }

    public String getPatientCondition() {
        return patientCondition;
    }

    public void setPatientCondition(String patientCondition) {
        this.patientCondition = patientCondition;
    }

    public String getAbnormalSituation() {
        return abnormalSituation;
    }

    public void setAbnormalSituation(String abnormalSituation) {
        this.abnormalSituation = abnormalSituation;
    }

    public String getMeasures() {
        return measures;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
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