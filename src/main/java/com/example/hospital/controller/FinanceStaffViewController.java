package com.example.hospital.controller;

import com.example.hospital.entity.FinanceStaffView;
import com.example.hospital.service.FinanceStaffViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 财务人员视图控制器
 */
@RestController
@RequestMapping("/api/finance-staff-views")
public class FinanceStaffViewController {
    
    @Autowired
    private FinanceStaffViewService financeStaffViewService;

    /**
     * 获取所有财务人员视图信息
     */
    @GetMapping
    public List<FinanceStaffView> getAllFinanceStaffViews() {
        return financeStaffViewService.getAllFinanceStaffViews();
    }

    /**
     * 根据用户ID查询财务人员视图信息
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getFinanceStaffViewById(@PathVariable String userId) {
        FinanceStaffView financeStaffView = financeStaffViewService.getFinanceStaffViewById(userId);
        if (financeStaffView != null) {
            return ResponseEntity.ok(financeStaffView);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 根据用户名查询财务人员视图信息
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getFinanceStaffViewByUsername(@PathVariable String username) {
        FinanceStaffView financeStaffView = financeStaffViewService.getFinanceStaffViewByUsername(username);
        if (financeStaffView != null) {
            return ResponseEntity.ok(financeStaffView);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 根据状态查询财务人员视图信息
     */
    @GetMapping("/status/{status}")
    public List<FinanceStaffView> getFinanceStaffViewsByStatus(@PathVariable String status) {
        return financeStaffViewService.getFinanceStaffViewsByStatus(status);
    }
} 