package com.example.hospital.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 医疗设备实体类
 * 对应数据库中的医疗设备表
 */
public class MedicalDevice {
    
    // 设备编号（如：D2025001）
    private String deviceId;
    
    // 设备名称
    private String deviceName;
    
    // 设备类型（监护仪/呼吸机）
    private String deviceType;
    
    // 型号
    private String modelNumber;
    
    // 生产厂家
    private String manufacturer;
    
    // 购买日期
    private LocalDate purchaseDate;
    
    // 保修截止日期
    private LocalDate warrantyDate;
    
    // 所属病房（可为空，表示公共设备）
    private String wardId;
    
    // 设备状态（正常/维修/报废）
    private String deviceStatus;
    
    // 创建时间
    private LocalDateTime createTime;
    
    // 更新时间
    private LocalDateTime updateTime;

    // Getters and Setters
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(LocalDate warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
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