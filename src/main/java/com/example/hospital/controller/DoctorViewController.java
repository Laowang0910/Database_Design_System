package com.example.hospital.controller;

import com.example.hospital.entity.DoctorView;
import com.example.hospital.service.DoctorViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 医生视图控制器
 */
@RestController
@RequestMapping("/api/doctor-views")
public class DoctorViewController {
    
    @Autowired
    private DoctorViewService doctorViewService;

    /**
     * 获取所有医生视图信息
     */
    @GetMapping
    public List<DoctorView> getAllDoctorViews() {
        return doctorViewService.getAllDoctorViews();
    }

    /**
     * 根据员工ID查询医生视图信息
     */
    @GetMapping("/{staffId}")
    public ResponseEntity<?> getDoctorViewById(@PathVariable String staffId) {
        DoctorView doctorView = doctorViewService.getDoctorViewById(staffId);
        if (doctorView != null) {
            return ResponseEntity.ok(doctorView);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 根据科室查询医生视图信息
     */
    @GetMapping("/department/{department}")
    public List<DoctorView> getDoctorViewsByDepartment(@PathVariable String department) {
        return doctorViewService.getDoctorViewsByDepartment(department);
    }

    /**
     * 根据职称查询医生视图信息
     */
    @GetMapping("/title/{title}")
    public List<DoctorView> getDoctorViewsByTitle(@PathVariable String title) {
        return doctorViewService.getDoctorViewsByTitle(title);
    }

    /**
     * 根据姓名模糊查询医生视图信息
     */
    @GetMapping("/name/{staffName}")
    public List<DoctorView> getDoctorViewsByName(@PathVariable String staffName) {
        return doctorViewService.getDoctorViewsByName(staffName);
    }
} 