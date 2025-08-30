package com.example.hospital.entity;

import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 床位实体类
 * 对应数据库中的床位信息表
 */
public class Bed {
    // 床位ID
    private String bedId;
    
    // 病房ID
    private String wardId;
    
    // 床位号
    private Integer bedNumber;
    
    // 床位状态（空闲/已占用）
    private String bedStatus;  // AVAILABLE, OCCUPIED, MAINTENANCE

    // 床位价格（元/天）
    private BigDecimal bedPrice;
    
    // 创建时间
    private LocalDateTime createTime;
    
    // 更新时间
    private LocalDateTime updateTime;

    // Getters and Setters
    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public Integer getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(Integer bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getBedStatus() {
        return bedStatus;
    }

    public void setBedStatus(String bedStatus) {
        this.bedStatus = bedStatus;
    }

    public BigDecimal getBedPrice() {
        return bedPrice;
    }

    public void setBedPrice(BigDecimal bedPrice) {
        this.bedPrice = bedPrice;
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