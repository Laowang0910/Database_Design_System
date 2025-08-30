package com.example.hospital.entity;

import java.time.LocalDateTime;

public class PatientBedInfo {
    private String patientId;
    private String bedId;
    private LocalDateTime assignTime;
    private String assignStatus;
    private LocalDateTime cerateTime;
    private LocalDateTime updateTime;

    // Getters and setters
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public LocalDateTime getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(LocalDateTime assignTime) {
        this.assignTime = assignTime;
    }

    public String getAssignStatus() {
        return assignStatus;
    }

    public void setAssignStatus(String assignStatus) {
        this.assignStatus = assignStatus;
    }

    public LocalDateTime getCerateTime() {
        return cerateTime;
    }

    public void setCerateTime(LocalDateTime cerateTime) {
        this.cerateTime = cerateTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
} 