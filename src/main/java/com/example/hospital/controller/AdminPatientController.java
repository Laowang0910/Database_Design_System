package com.example.hospital.controller;

import com.example.hospital.entity.Patient;
import com.example.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员患者控制器
 * 处理与患者相关的管理员操作请求
 * 
 * @RestController REST风格控制器，响应JSON格式数据
 * @RequestMapping 指定该控制器的基础URL路径
 */
@RestController
@RequestMapping("/admin/patients")
public class AdminPatientController {
    
    /**
     * 注入患者服务层组件
     * 通过@Autowired实现依赖注入，由Spring自动装配PatientService实例
     */
    @Autowired
    private PatientService patientService;

    /**
     * 获取所有患者信息
     * 
     * @GetMapping 表示这是一个处理GET请求的方法
     * @return 返回患者列表
     */
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    /**
     * 添加新患者
     * 
     * @PostMapping 表示这是一个处理POST请求的方法
     * @RequestBody 表示从请求体中获取患者信息
     * @param patient 待添加的患者信息
     * @return 返回添加结果
     */
    @PostMapping
    public Map<String, Object> addPatient(@RequestBody Patient patient) {
        Map<String, Object> result = new HashMap<>();
        int rows = patientService.addPatient(patient);
        if (rows > 0) {
            result.put("success", true);
            result.put("message", "添加患者成功");
        } else {
            result.put("success", false);
            result.put("message", "添加患者失败");
        }
        return result;
    }

    /**
     * 更新患者信息
     * 
     * @PutMapping 表示这是一个处理PUT请求的方法
     * @RequestBody 表示从请求体中获取患者信息
     * @param patient 待更新的患者信息
     * @return 返回更新结果
     */
    @PutMapping("/{patientId}")
    public Map<String, Object> updatePatient(@PathVariable String patientId, @RequestBody Patient patient) {
        Map<String, Object> result = new HashMap<>();
        patient.setPatientId(patientId);
        int rows = patientService.updatePatient(patient);
        if (rows > 0) {
            result.put("success", true);
            result.put("message", "更新患者信息成功");
        } else {
            result.put("success", false);
            result.put("message", "更新患者信息失败");
        }
        return result;
    }

    /**
     * 删除患者信息
     * 
     * @DeleteMapping 表示这是一个处理DELETE请求的方法
     * @PathVariable 表示从URL路径中获取患者ID
     * @param patientId 待删除的患者ID
     * @return 返回删除结果
     */
    @DeleteMapping("/{patientId}")
    public Map<String, Object> deletePatient(@PathVariable String patientId) {
        Map<String, Object> result = new HashMap<>();
        int rows = patientService.deletePatient(patientId);
        if (rows > 0) {
            result.put("success", true);
            result.put("message", "删除患者成功");
        } else {
            result.put("success", false);
            result.put("message", "删除患者失败");
        }
        return result;
    }
} 