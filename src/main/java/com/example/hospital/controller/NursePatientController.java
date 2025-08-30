package com.example.hospital.controller;

import com.example.hospital.entity.Patient;
import com.example.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/nurse/patients")
public class NursePatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping
    public int addPatient(@RequestBody Patient patient) {
        patient.setPatientId(java.util.UUID.randomUUID().toString());
        LocalDateTime now = LocalDateTime.now();
        patient.setCreateTime(now);
        patient.setUpdateTime(now);
        return patientService.addPatient(patient);
    }

    /**
     * 为患者分配病房
     */
    @PostMapping("/assign-bed")
    public ResponseEntity<?> assignBed(@RequestBody Map<String, String> request) {
        String patientId = request.get("patientId");
        String wardId = request.get("wardId");
        String bedNumber = request.get("bedNumber");

        Map<String, Object> response = new HashMap<>();
        try {
            int result = patientService.assignBed(patientId, wardId, Integer.parseInt(bedNumber));
            if (result > 0) {
                response.put("success", true);
                response.put("message", "病房分配成功");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "病房分配失败，该床位可能已被占用");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (NumberFormatException e) {
            response.put("success", false);
            response.put("message", "床位号格式错误");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "病房分配失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
} 