package com.example.hospital.entity;

public class DoctorPatientVisit {

    private String staffId;
    private String patientId;
    private String state;

    // Getters and Setters
    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
} 