package com.example.hospital.entity;

import java.time.LocalDateTime;

/**
 * 病房实体类
 */
public class Ward {
    // 病房编号
    private String wardId;
    
    // 病房名称
    private String wardName;
    
    // 病房类型（普通/重症/隔离）
    private String wardType;
    
    // 楼层号
    private int floorNumber;
    
    // 总床位数
    private int totalBeds;
    
    // 可用床位数
    private int availableBeds;
    
    // 已用床位数
    private int occupiedBeds;
    
    // 病房状态（开放/关闭）
    private String wardStatus;
    
    // 创建时间
    private LocalDateTime createTime;
    
    // 更新时间
    private LocalDateTime updateTime;

    // Getters and Setters
    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getWardType() {
        return wardType;
    }

    public void setWardType(String wardType) {
        this.wardType = wardType;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getTotalBeds() {
        return totalBeds;
    }

    public void setTotalBeds(int totalBeds) {
        this.totalBeds = totalBeds;
        // 更新已用床位数
        this.occupiedBeds = totalBeds - this.availableBeds;
    }

    public int getAvailableBeds() {
        return availableBeds;
    }

    public void setAvailableBeds(int availableBeds) {
        this.availableBeds = availableBeds;
        // 更新已用床位数
        this.occupiedBeds = this.totalBeds - availableBeds;
    }

    public int getOccupiedBeds() {
        return occupiedBeds;
    }

    public void setOccupiedBeds(int occupiedBeds) {
        this.occupiedBeds = occupiedBeds;
        // 更新可用床位数
        this.availableBeds = this.totalBeds - occupiedBeds;
    }

    public String getWardStatus() {
        return wardStatus;
    }

    public void setWardStatus(String wardStatus) {
        this.wardStatus = wardStatus;
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