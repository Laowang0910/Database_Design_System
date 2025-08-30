package com.example.hospital.service;

import com.example.hospital.entity.NurseView;
import com.example.hospital.mapper.NurseViewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 护士视图服务层
 */
@Service
public class NurseViewService {
    
    @Autowired
    private NurseViewMapper nurseViewMapper;

    /**
     * 获取所有护士视图信息
     */
    public List<NurseView> getAllNurseViews() {
        return nurseViewMapper.getAllNurseViews();
    }

    /**
     * 根据员工ID查询护士视图信息
     */
    public NurseView getNurseViewById(String staffId) {
        return nurseViewMapper.getNurseViewById(staffId);
    }

    /**
     * 根据科室查询护士视图信息
     */
    public List<NurseView> getNurseViewsByDepartment(String department) {
        return nurseViewMapper.getNurseViewsByDepartment(department);
    }

    /**
     * 根据职称查询护士视图信息
     */
    public List<NurseView> getNurseViewsByTitle(String title) {
        return nurseViewMapper.getNurseViewsByTitle(title);
    }

    /**
     * 根据姓名模糊查询护士视图信息
     */
    public List<NurseView> getNurseViewsByName(String staffName) {
        return nurseViewMapper.getNurseViewsByName(staffName);
    }
} 