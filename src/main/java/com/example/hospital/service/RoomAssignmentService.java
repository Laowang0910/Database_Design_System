package com.example.hospital.service;

import com.example.hospital.entity.PatientBedInfo;
import com.example.hospital.mapper.PatientBedInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RoomAssignmentService {

    @Autowired
    private PatientBedInfoMapper patientBedInfoMapper;

    public void assignRoom(String patientId, String wardId, String bedId) {
        PatientBedInfo assignment = new PatientBedInfo();
        assignment.setPatientId(patientId);
        assignment.setBedId(bedId);
        LocalDateTime now = LocalDateTime.now();
        assignment.setAssignTime(now);
        assignment.setCerateTime(now);
        assignment.setUpdateTime(now);
        assignment.setAssignStatus("有效"); // Set initial status to '有效'

        patientBedInfoMapper.insertPatientBedInfo(assignment);
    }

    public void dischargePatient(String patientId, String bedId) {
        patientBedInfoMapper.invalidatePatientBedInfo(patientId, bedId);
        patientBedInfoMapper.deletePatientBedInfo(patientId, bedId);
    }
} 