package com.example.hospital.controller;

import com.example.hospital.entity.PatientCallInfoView;
import com.example.hospital.mapper.PatientCallInfoViewMapper;
import com.example.hospital.mapper.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/patient-calls")
public class PatientCallInfoViewController {
    @Autowired
    private PatientCallInfoViewMapper patientCallInfoViewMapper;
    @Autowired
    private PatientMapper patientMapper;

    @GetMapping
    public List<PatientCallInfoView> getAllCallingPatients() {
        return patientCallInfoViewMapper.getAllCallingPatients();
    }

    @PutMapping("/{patientId}")
    public void setCallStatusToProcessed(@PathVariable String patientId) {
        patientMapper.updateCallStatusToProcessed(patientId);
    }

    @PutMapping("/{patientId}/request")
    public void setCallStatusToRequest(@PathVariable String patientId) {
        patientMapper.updateCallStatusToRequest(patientId);
        System.out.println("patientId: [" + patientId + "]");
    }
} 