package com.example.hospital.controller;

import com.example.hospital.entity.Ward;
import com.example.hospital.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 病房管理控制器
 * 处理与病房相关的请求
 */
@RestController
@RequestMapping("/api/wards")
public class WardController {
    
    @Autowired
    private WardService wardService;

    /**
     * 获取所有病房信息
     * 
     * @return 返回病房列表
     */
    @GetMapping
    public List<Ward> getAllWards() {
        return wardService.getAllWards();
    }

    /**
     * 根据ID获取病房信息
     * 
     * @param wardId 病房ID
     * @return 返回病房信息
     */
    @GetMapping("/{wardId}")
    public Ward getWardById(@PathVariable String wardId) {
        return wardService.getWardById(wardId);
    }

    /**
     * 添加新病房
     * 
     * @param ward 待添加的病房信息
     * @return 返回添加结果
     */
    @PostMapping
    public Map<String, Object> addWard(@RequestBody Ward ward) {
        Map<String, Object> result = new HashMap<>();
        int rows = wardService.addWard(ward);
        if (rows > 0) {
            result.put("success", true);
            result.put("message", "添加病房成功");
        } else {
            result.put("success", false);
            result.put("message", "添加病房失败");
        }
        return result;
    }

    /**
     * 更新病房信息
     * 
     * @param wardId 病房ID
     * @param ward 待更新的病房信息
     * @return 返回更新结果
     */
    @PutMapping("/{wardId}")
    public Map<String, Object> updateWard(@PathVariable String wardId, @RequestBody Ward ward) {
        Map<String, Object> result = new HashMap<>();
        ward.setWardId(wardId);
        int rows = wardService.updateWard(ward);
        if (rows > 0) {
            result.put("success", true);
            result.put("message", "更新病房信息成功");
        } else {
            result.put("success", false);
            result.put("message", "更新病房信息失败");
        }
        return result;
    }

    /**
     * 删除病房信息
     * 
     * @param wardId 待删除的病房ID
     * @return 返回删除结果
     */
    @DeleteMapping("/{wardId}")
    public Map<String, Object> deleteWard(@PathVariable String wardId) {
        Map<String, Object> result = new HashMap<>();
        int rows = wardService.deleteWard(wardId);
        if (rows > 0) {
            result.put("success", true);
            result.put("message", "删除病房成功");
        } else {
            result.put("success", false);
            result.put("message", "删除病房失败");
        }
        return result;
    }
} 