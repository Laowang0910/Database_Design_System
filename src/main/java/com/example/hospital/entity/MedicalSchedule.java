package com.example.hospital.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 医护人员排班实体类
 * 对应数据库中的医护人员排班表
 */
public class MedicalSchedule {
    
    // 排班编号
    private String scheduleId;
    
    // 员工编号
    private String staffId;
    
    // 负责病房
    private String wardId;
    
    // 排班日期
    private LocalDate scheduleDate;
    
    // 班次（早班/中班/夜班）
    private String shiftType;
    
    // 值班状态
    private String dutyStatus;
    
    // 创建时间
    private LocalDateTime createTime;
    
    // 更新时间
    private LocalDateTime updateTime;

    // Getters and Setters
    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    public String getDutyStatus() {
        return dutyStatus;
    }

    public void setDutyStatus(String dutyStatus) {
        this.dutyStatus = dutyStatus;
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