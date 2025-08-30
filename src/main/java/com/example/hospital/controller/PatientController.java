package com.example.hospital.controller;

import com.example.hospital.entity.Patient;
import com.example.hospital.service.PatientService;
import com.example.hospital.mapper.PatientBedInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 患者管理控制器
 * 处理与患者相关的请求
 */
@RestController
@RequestMapping("/api/patients")
@CrossOrigin
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientBedInfoMapper patientBedInfoMapper;

    /**
     * 获取所有患者信息
     */
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    /**
     * 根据ID获取患者信息
     */
    @GetMapping("/{patientId}")
    public ResponseEntity<?> getPatientById(@PathVariable String patientId) {
        Patient patient = patientService.getPatientById(patientId);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "未找到该患者");
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 添加新患者
     */
    @PostMapping
    public ResponseEntity<?> addPatient(@RequestBody Patient patient) {
        patient.setCreateTime(LocalDateTime.now());
        patient.setUpdateTime(LocalDateTime.now());
        int result = patientService.addPatient(patient);
        
        Map<String, String> response = new HashMap<>();
        if (result > 0) {
            response.put("message", "添加成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "添加失败");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 更新患者信息
     */
    @PutMapping("/{patientId}")
    public ResponseEntity<?> updatePatient(@PathVariable String patientId, @RequestBody Patient patient) {
        patient.setPatientId(patientId);
        patient.setUpdateTime(LocalDateTime.now());
        int result = patientService.updatePatient(patient);
        
        Map<String, String> response = new HashMap<>();
        if (result > 0) {
            response.put("message", "更新成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "更新失败");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 删除患者信息
     */
    @DeleteMapping("/{patientId}")
    public ResponseEntity<?> deletePatient(@PathVariable String patientId) {
        int result = patientService.deletePatient(patientId);
        
        Map<String, String> response = new HashMap<>();
        if (result > 0) {
            response.put("message", "删除成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "删除失败");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 为患者分配床位
     */
    @PostMapping("/{patientId}/assign-bed")
    public ResponseEntity<?> assignBed(
            @PathVariable String patientId,
            @RequestParam String wardId,
            @RequestParam Integer bedNumber) {
        
        int result = patientService.assignBed(patientId, wardId, bedNumber);
        
        Map<String, String> response = new HashMap<>();
        if (result > 0) {
            response.put("message", "床位分配成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "床位分配失败");
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 新增接口：通过 patientId 查询当前床位 bedId
    @GetMapping("/patient-bed")
    @ResponseBody
    public Map<String, Object> getPatientBed(@RequestParam String patientId) {
        String bedId = patientBedInfoMapper.findCurrentBedIdByPatientId(patientId);
        Map<String, Object> result = new HashMap<>();
        result.put("bedId", bedId);
        return result;
    }
} 