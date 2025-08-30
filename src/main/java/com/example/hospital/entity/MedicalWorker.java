package com.example.hospital.entity;

import java.time.LocalDateTime;

/**
 * 医务人员实体类
 * 对应数据库中的医务人员信息表
 */
public class MedicalWorker {
    
    // 员工编号
    private String staffId;
    
    // 员工姓名
    private String staffName;
    
    // 性别
    private String gender;
    
    // 年龄
    private Integer age;
    
    // 身份证号
    private String idNumber;
    
    // 联系电话
    private String phoneNumber;
    
    // 地址
    private String address;
    
    // 所属科室
    private String department;
    
    // 职位
    private String position;
    
    // 职称
    private String title;
    
    // 状态
    private String status;
    
    // 入职时间
    private LocalDateTime entryTime;
    
    // 创建时间
    private LocalDateTime createTime;
    
    // 更新时间
    private LocalDateTime updateTime;

    // Getters and Setters
    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
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

    @Override
    public String toString() {
        return "MedicalWorker{" +
                "staffId='" + staffId + '\'' +
                ", staffName='" + staffName + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", idNumber='" + idNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", entryTime=" + entryTime +
                '}';
    }
} 