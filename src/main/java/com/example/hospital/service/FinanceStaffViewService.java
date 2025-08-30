package com.example.hospital.service;

import com.example.hospital.entity.FinanceStaffView;
import com.example.hospital.mapper.FinanceStaffViewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 财务人员视图服务层
 */
@Service
public class FinanceStaffViewService {
    
    @Autowired
    private FinanceStaffViewMapper financeStaffViewMapper;

    /**
     * 获取所有财务人员视图信息
     */
    public List<FinanceStaffView> getAllFinanceStaffViews() {
        return financeStaffViewMapper.getAllFinanceStaffViews();
    }

    /**
     * 根据用户ID查询财务人员视图信息
     */
    public FinanceStaffView getFinanceStaffViewById(String userId) {
        return financeStaffViewMapper.getFinanceStaffViewById(userId);
    }

    /**
     * 根据用户名查询财务人员视图信息
     */
    public FinanceStaffView getFinanceStaffViewByUsername(String username) {
        return financeStaffViewMapper.getFinanceStaffViewByUsername(username);
    }

    /**
     * 根据状态查询财务人员视图信息
     */
    public List<FinanceStaffView> getFinanceStaffViewsByStatus(String status) {
        return financeStaffViewMapper.getFinanceStaffViewsByStatus(status);
    }
} 