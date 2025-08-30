package com.example.hospital.controller;

import com.example.hospital.entity.Prescription;
import com.example.hospital.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@CrossOrigin
public class PrescriptionController {
    
    @Autowired
    private PrescriptionService prescriptionService;
    
    /**
     * 获取所有处方
     */
    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();
        return ResponseEntity.ok(prescriptions);
    }
    
    /**
     * 根据患者ID获取处方
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByPatientId(@PathVariable String patientId) {
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByPatientId(patientId);
        return ResponseEntity.ok(prescriptions);
    }
    
    /**
     * 根据处方ID获取处方
     */
    @GetMapping("/{prescriptionId}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable String prescriptionId) {
        Prescription prescription = prescriptionService.getPrescriptionById(prescriptionId);
        if (prescription != null) {
            return ResponseEntity.ok(prescription);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 执行医嘱
     */
    @PostMapping("/{prescriptionId}/execute")
    public ResponseEntity<Void> executePrescription(@PathVariable String prescriptionId) {
        boolean success = prescriptionService.executePrescription(prescriptionId);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 更新处方信息
     */
    @PutMapping("/{prescriptionId}")
    public ResponseEntity<?> updatePrescription(@PathVariable String prescriptionId, @RequestBody Prescription prescription) {
        prescription.setPrescriptionId(prescriptionId);
        boolean success = prescriptionService.updatePrescription(prescription);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("更新失败");
        }
    }
} 