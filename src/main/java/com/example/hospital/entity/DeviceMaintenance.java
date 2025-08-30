package com.example.hospital.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 设备维护记录实体类
 * 对应数据库中的设备维护记录表
 */
public class DeviceMaintenance {
    
    // 维护记录编号
    private String maintenanceId;
    
    // 设备编号
    private String deviceId;
    
    // 维护类型（定期保养/故障维修）
    private String maintenanceType;
    
    // 维护开始时间
    private LocalDateTime startTime;
    
    // 维护结束时间
    private LocalDateTime endTime;
    
    // 维护人员
    private String maintainer;
    
    // 维护内容
    private String maintenanceContent;
    
    // 维护费用
    private BigDecimal maintenanceCost;
    
    // 维护结果（完成/未完成）
    private String maintenanceResult;
    
    // 备注
    private String remarks;
    
    // 创建时间
    private LocalDateTime createTime;
    
    // 更新时间
    private LocalDateTime updateTime;

    // Getters and Setters
    public String getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(String maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(String maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getMaintainer() {
        return maintainer;
    }

    public void setMaintainer(String maintainer) {
        this.maintainer = maintainer;
    }

    public String getMaintenanceContent() {
        return maintenanceContent;
    }

    public void setMaintenanceContent(String maintenanceContent) {
        this.maintenanceContent = maintenanceContent;
    }

    public BigDecimal getMaintenanceCost() {
        return maintenanceCost;
    }

    public void setMaintenanceCost(BigDecimal maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }

    public String getMaintenanceResult() {
        return maintenanceResult;
    }

    public void setMaintenanceResult(String maintenanceResult) {
        this.maintenanceResult = maintenanceResult;
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