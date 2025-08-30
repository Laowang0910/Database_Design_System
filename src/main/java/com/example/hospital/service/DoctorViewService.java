package com.example.hospital.service;

import com.example.hospital.entity.DoctorView;
import com.example.hospital.mapper.DoctorViewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 医生视图服务层
 */
@Service
public class DoctorViewService {
    
    @Autowired
    private DoctorViewMapper doctorViewMapper;

    /**
     * 获取所有医生视图信息
     */
    public List<DoctorView> getAllDoctorViews() {
        return doctorViewMapper.getAllDoctorViews();
    }

    /**
     * 根据员工ID查询医生视图信息
     */
    public DoctorView getDoctorViewById(String staffId) {
        return doctorViewMapper.getDoctorViewById(staffId);
    }

    /**
     * 根据科室查询医生视图信息
     */
    public List<DoctorView> getDoctorViewsByDepartment(String department) {
        return doctorViewMapper.getDoctorViewsByDepartment(department);
    }

    /**
     * 根据职称查询医生视图信息
     */
    public List<DoctorView> getDoctorViewsByTitle(String title) {
        return doctorViewMapper.getDoctorViewsByTitle(title);
    }

    /**
     * 根据姓名模糊查询医生视图信息
     */
    public List<DoctorView> getDoctorViewsByName(String staffName) {
        return doctorViewMapper.getDoctorViewsByName(staffName);
    }
} 