package com.example.hospital.entity;

import java.time.LocalDateTime;

/**
 * 病房转移记录实体类
 * 对应数据库中的病房转移记录表
 */
public class WardTransfer {
    
    // 转移记录编号
    private String transferId;
    
    // 患者编号
    private String patientId;
    
    // 原病房编号
    private String sourceWardId;
    
    // 原床位编号
    private String sourceBedId;
    
    // 目标病房编号
    private String targetWardId;
    
    // 目标床位编号
    private String targetBedId;
    
    // 转移原因
    private String transferReason;
    
    // 转移时间
    private LocalDateTime transferTime;
    
    // 经办人编号
    private String handlerId;
    
    // 转移状态（待转移/已完成/已取消）
    private String transferStatus;
    
    // 备注
    private String remarks;
    
    // 创建时间
    private LocalDateTime createTime;
    
    // 更新时间
    private LocalDateTime updateTime;

    // Getters and Setters
    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getSourceWardId() {
        return sourceWardId;
    }

    public void setSourceWardId(String sourceWardId) {
        this.sourceWardId = sourceWardId;
    }

    public String getSourceBedId() {
        return sourceBedId;
    }

    public void setSourceBedId(String sourceBedId) {
        this.sourceBedId = sourceBedId;
    }

    public String getTargetWardId() {
        return targetWardId;
    }

    public void setTargetWardId(String targetWardId) {
        this.targetWardId = targetWardId;
    }

    public String getTargetBedId() {
        return targetBedId;
    }

    public void setTargetBedId(String targetBedId) {
        this.targetBedId = targetBedId;
    }

    public String getTransferReason() {
        return transferReason;
    }

    public void setTransferReason(String transferReason) {
        this.transferReason = transferReason;
    }

    public LocalDateTime getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(LocalDateTime transferTime) {
        this.transferTime = transferTime;
    }

    public String getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(String handlerId) {
        this.handlerId = handlerId;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
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