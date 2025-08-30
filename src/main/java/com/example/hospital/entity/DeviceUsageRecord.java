package com.example.hospital.entity;

import java.time.LocalDateTime;

/**
 * 设备使用记录实体类
 * 对应数据库中的设备使用记录表
 */
public class DeviceUsageRecord {

    private String deviceId;
    private LocalDateTime serviceTime;
    private String useAnnotations;

    // Getters and Setters
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public LocalDateTime getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(LocalDateTime serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getUseAnnotations() {
        return useAnnotations;
    }

    public void setUseAnnotations(String useAnnotations) {
        this.useAnnotations = useAnnotations;
    }
} 