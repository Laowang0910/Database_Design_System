package com.example.hospital.entity;

import java.time.LocalDateTime;

/**
 * 患者实体类
 * 对应数据库中的患者信息表
 */
public class Patient {
    
    // 患者编号
    private String patientId;
    
    // 患者姓名
    private String patientName;
    
    // 性别
    private String gender;
    
    // 年龄
    private Integer age;
    
    // 身份证号
    private String idNumber;
    
    // 联系电话
    private String phoneNumber;
    
    // 家庭地址
    private String address;
    
    // 紧急联系人姓名
    private String emergencyContact;
    
    // 紧急联系人电话
    private String emergencyPhone;
    
    // 入院时间
    private LocalDateTime admissionTime;
    
    // 出院时间
    private LocalDateTime dischargeTime;
    
    // 患者状态（住院/出院/转院）
    private String patientStatus;
    
    // 诊断结果
    private String diagnosis;
    
    // 床位相关信息
    private String wardId;
    private Integer bedNumber;
    private String bedId;
    
    // 创建时间
    private LocalDateTime createTime;
    
    // 更新时间
    private LocalDateTime updateTime;

    // 呼叫状态
    private String callStatus;

    // Getters and Setters
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public LocalDateTime getAdmissionTime() {
        return admissionTime;
    }

    public void setAdmissionTime(LocalDateTime admissionTime) {
        this.admissionTime = admissionTime;
    }

    public LocalDateTime getDischargeTime() {
        return dischargeTime;
    }

    public void setDischargeTime(LocalDateTime dischargeTime) {
        this.dischargeTime = dischargeTime;
    }

    public String getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(String patientStatus) {
        this.patientStatus = patientStatus;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
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

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
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

    public String getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId='" + patientId + '\'' +
                ", patientName='" + patientName + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", idNumber='" + idNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", emergencyContact='" + emergencyContact + '\'' +
                ", emergencyPhone='" + emergencyPhone + '\'' +
                ", admissionTime=" + admissionTime +
                ", dischargeTime=" + dischargeTime +
                ", patientStatus='" + patientStatus + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", wardId='" + wardId + '\'' +
                ", bedNumber=" + bedNumber +
                ", callStatus='" + callStatus + '\'' +
                '}';
    }
} 