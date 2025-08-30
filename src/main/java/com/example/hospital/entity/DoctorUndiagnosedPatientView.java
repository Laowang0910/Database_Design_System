package com.example.hospital.entity;

public class DoctorUndiagnosedPatientView {

    private String doctorStaffId;
    private String doctorName;
    private String patientId;
    private String patientName;
    private String visitState;

    // Getters and Setters
    public String getDoctorStaffId() {
        return doctorStaffId;
    }

    public void setDoctorStaffId(String doctorStaffId) {
        this.doctorStaffId = doctorStaffId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

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

    public String getVisitState() {
        return visitState;
    }

    public void setVisitState(String visitState) {
        this.visitState = visitState;
    }
} 