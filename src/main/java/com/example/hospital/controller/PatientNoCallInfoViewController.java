package com.example.hospital.controller;

import com.example.hospital.entity.PatientNoCallInfoView;
import com.example.hospital.mapper.PatientNoCallInfoViewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/patient-nocalls")
public class PatientNoCallInfoViewController {
    @Autowired
    private PatientNoCallInfoViewMapper patientNoCallInfoViewMapper;

    @GetMapping
    public List<PatientNoCallInfoView> getNoCallPatients(@RequestParam String patientId) {
        return patientNoCallInfoViewMapper.getNoCallPatientsByPatientId(patientId);
    }
} 