package com.example.hospital.controller;

import com.example.hospital.entity.NurseView;
import com.example.hospital.service.NurseViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 护士视图控制器
 */
@RestController
@RequestMapping("/api/nurse-views")
public class NurseViewController {
    
    @Autowired
    private NurseViewService nurseViewService;

    /**
     * 获取所有护士视图信息
     */
    @GetMapping
    public List<NurseView> getAllNurseViews() {
        return nurseViewService.getAllNurseViews();
    }

    /**
     * 根据员工ID查询护士视图信息
     */
    @GetMapping("/{staffId}")
    public ResponseEntity<?> getNurseViewById(@PathVariable String staffId) {
        NurseView nurseView = nurseViewService.getNurseViewById(staffId);
        if (nurseView != null) {
            return ResponseEntity.ok(nurseView);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 根据科室查询护士视图信息
     */
    @GetMapping("/department/{department}")
    public List<NurseView> getNurseViewsByDepartment(@PathVariable String department) {
        return nurseViewService.getNurseViewsByDepartment(department);
    }

    /**
     * 根据职称查询护士视图信息
     */
    @GetMapping("/title/{title}")
    public List<NurseView> getNurseViewsByTitle(@PathVariable String title) {
        return nurseViewService.getNurseViewsByTitle(title);
    }

    /**
     * 根据姓名模糊查询护士视图信息
     */
    @GetMapping("/name/{staffName}")
    public List<NurseView> getNurseViewsByName(@PathVariable String staffName) {
        return nurseViewService.getNurseViewsByName(staffName);
    }
} 