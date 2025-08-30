package com.example.hospital.controller;

import com.example.hospital.entity.MedicalWorker;
import com.example.hospital.service.MedicalWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 医护人员管理控制器
 * 处理与医护人员相关的请求
 */
@RestController
@RequestMapping("/api/medical-workers")
@CrossOrigin
public class MedicalWorkerController {
    
    @Autowired
    private MedicalWorkerService medicalWorkerService;

    /**
     * 获取所有医护人员信息
     * 
     * @return 返回医护人员列表
     */
    @GetMapping
    public ResponseEntity<List<MedicalWorker>> getAllMedicalWorkers() {
        List<MedicalWorker> medicalWorkers = medicalWorkerService.getAllMedicalWorkers();
        return ResponseEntity.ok(medicalWorkers);
    }

    /**
     * 根据ID获取医护人员信息
     * 
     * @param staffId 员工ID
     * @return 返回医护人员信息
     */
    @GetMapping("/{staffId}")
    public ResponseEntity<?> getMedicalWorkerById(@PathVariable String staffId) {
        MedicalWorker medicalWorker = medicalWorkerService.getMedicalWorkerById(staffId);
        if (medicalWorker != null) {
            return ResponseEntity.ok(medicalWorker);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "未找到该医务人员");
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 根据科室获取医护人员信息
     * 
     * @param department 科室名称
     * @return 返回该科室的医护人员列表
     */
    @GetMapping("/department/{department}")
    public List<MedicalWorker> getMedicalWorkersByDepartment(@PathVariable String department) {
        return medicalWorkerService.getMedicalWorkersByDepartment(department);
    }

    /**
     * 添加新医护人员
     * 
     * @param medicalWorker 待添加的医护人员信息
     * @return 返回添加结果
     */
    @PostMapping
    public ResponseEntity<?> addMedicalWorker(@RequestBody MedicalWorker medicalWorker) {
        medicalWorker.setCreateTime(LocalDateTime.now());
        medicalWorker.setUpdateTime(LocalDateTime.now());
        int result = medicalWorkerService.addMedicalWorker(medicalWorker);
        
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
     * 更新医护人员信息
     * 
     * @param staffId 员工ID
     * @param medicalWorker 待更新的医护人员信息
     * @return 返回更新结果
     */
    @PutMapping("/{staffId}")
    public ResponseEntity<?> updateMedicalWorker(@PathVariable String staffId, @RequestBody MedicalWorker medicalWorker) {
        medicalWorker.setStaffId(staffId);
        medicalWorker.setUpdateTime(LocalDateTime.now());
        int result = medicalWorkerService.updateMedicalWorker(medicalWorker);
        
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
     * 删除医护人员信息
     * 
     * @param staffId 待删除的员工ID
     * @return 返回删除结果
     */
    @DeleteMapping("/{staffId}")
    public ResponseEntity<?> deleteMedicalWorker(@PathVariable String staffId) {
        int result = medicalWorkerService.deleteMedicalWorker(staffId);
        
        Map<String, String> response = new HashMap<>();
        if (result > 0) {
            response.put("message", "删除成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "删除失败");
            return ResponseEntity.badRequest().body(response);
        }
    }
} 