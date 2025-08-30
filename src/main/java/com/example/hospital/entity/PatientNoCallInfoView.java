package com.example.hospital.entity;

public class PatientNoCallInfoView {
    private String patientId;
    private String callStatus;
    private String bedId;
    private String wardId;
    private String wardName;
    private Integer bedNumber;

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getCallStatus() { return callStatus; }
    public void setCallStatus(String callStatus) { this.callStatus = callStatus; }

    public String getBedId() { return bedId; }
    public void setBedId(String bedId) { this.bedId = bedId; }

    public String getWardId() { return wardId; }
    public void setWardId(String wardId) { this.wardId = wardId; }

    public String getWardName() { return wardName; }
    public void setWardName(String wardName) { this.wardName = wardName; }

    public Integer getBedNumber() { return bedNumber; }
    public void setBedNumber(Integer bedNumber) { this.bedNumber = bedNumber; }
} 