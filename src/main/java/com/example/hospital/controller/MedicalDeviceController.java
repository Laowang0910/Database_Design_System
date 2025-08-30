package com.example.hospital.controller;

import com.example.hospital.entity.MedicalDevice;
import com.example.hospital.service.MedicalDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 医疗设备管理控制器
 * 处理与医疗设备相关的请求
 */
@RestController
@RequestMapping("/api/medical-devices")
public class MedicalDeviceController {
    
    @Autowired
    private MedicalDeviceService medicalDeviceService;

    /**
     * 获取所有医疗设备信息
     * 
     * @return 返回医疗设备列表
     */
    @GetMapping
    public List<MedicalDevice> getAllMedicalDevices() {
        return medicalDeviceService.getAllMedicalDevices();
    }

    /**
     * 根据ID获取医疗设备信息
     * 
     * @param deviceId 设备ID
     * @return 返回医疗设备信息
     */
    @GetMapping("/{deviceId}")
    public MedicalDevice getMedicalDeviceById(@PathVariable String deviceId) {
        return medicalDeviceService.getMedicalDeviceById(deviceId);
    }

    /**
     * 根据设备类型获取医疗设备信息
     * 
     * @param deviceType 设备类型
     * @return 返回该类型的医疗设备列表
     */
    @GetMapping("/type/{deviceType}")
    public List<MedicalDevice> getMedicalDevicesByType(@PathVariable String deviceType) {
        return medicalDeviceService.getMedicalDevicesByType(deviceType);
    }

    /**
     * 添加新医疗设备
     * 
     * @param medicalDevice 待添加的医疗设备信息
     * @return 返回添加结果
     */
    @PostMapping
    public Map<String, Object> addMedicalDevice(@RequestBody MedicalDevice medicalDevice) {
        Map<String, Object> result = new HashMap<>();
        int rows = medicalDeviceService.addMedicalDevice(medicalDevice);
        if (rows > 0) {
            result.put("success", true);
            result.put("message", "添加医疗设备成功");
        } else {
            result.put("success", false);
            result.put("message", "添加医疗设备失败");
        }
        return result;
    }

    /**
     * 更新医疗设备信息
     * 
     * @param deviceId 设备ID
     * @param medicalDevice 待更新的医疗设备信息
     * @return 返回更新结果
     */
    @PutMapping("/{deviceId}")
    public Map<String, Object> updateMedicalDevice(@PathVariable String deviceId, @RequestBody MedicalDevice medicalDevice) {
        Map<String, Object> result = new HashMap<>();
        medicalDevice.setDeviceId(deviceId);
        int rows = medicalDeviceService.updateMedicalDevice(medicalDevice);
        if (rows > 0) {
            result.put("success", true);
            result.put("message", "更新医疗设备信息成功");
        } else {
            result.put("success", false);
            result.put("message", "更新医疗设备信息失败");
        }
        return result;
    }

    /**
     * 删除医疗设备信息
     * 
     * @param deviceId 待删除的设备ID
     * @return 返回删除结果
     */
    @DeleteMapping("/{deviceId}")
    public Map<String, Object> deleteMedicalDevice(@PathVariable String deviceId) {
        Map<String, Object> result = new HashMap<>();
        int rows = medicalDeviceService.deleteMedicalDevice(deviceId);
        if (rows > 0) {
            result.put("success", true);
            result.put("message", "删除医疗设备成功");
        } else {
            result.put("success", false);
            result.put("message", "删除医疗设备失败");
        }
        return result;
    }
} 