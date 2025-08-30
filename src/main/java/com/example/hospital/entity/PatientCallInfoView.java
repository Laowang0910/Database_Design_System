package com.example.hospital.entity;

public class PatientCallInfoView {
    private String patientId;
    private String bedId;
    private String wardId;
    private Integer bedNumber;
    private String wardName;
    private String callStatus;

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
    public String getWardName() {
        return wardName;
    }
    public void setWardName(String wardName) {
        this.wardName = wardName;
    }
    public String getCallStatus() {
        return callStatus;
    }
    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }
} 