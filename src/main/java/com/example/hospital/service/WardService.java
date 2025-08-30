package com.example.hospital.service;

import com.example.hospital.entity.Ward;
import com.example.hospital.mapper.WardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;

/**
 * 病房服务层
 * 处理与病房相关的业务逻辑
 */
@Service
public class WardService {
    
    @Autowired
    private WardMapper wardMapper;

    /**
     * 获取所有病房信息
     * 
     * @return 返回病房信息列表
     */
    public List<Ward> getAllWards() {
        return wardMapper.getAllWards();
    }

    /**
     * 根据病房ID查询病房信息
     * 
     * @param wardId 病房ID
     * @return 返回病房信息
     */
    public Ward getWardById(String wardId) {
        return wardMapper.getWardById(wardId);
    }

    /**
     * 添加新病房
     * 
     * @param ward 待添加的病房信息
     * @return 返回影响的行数，1表示添加成功，0表示添加失败
     */
    public int addWard(Ward ward) {
        ward.setCreateTime(LocalDateTime.now());
        ward.setUpdateTime(LocalDateTime.now());
        // 初始化已用床位数为0
        ward.setOccupiedBeds(0);
        return wardMapper.insertWard(ward);
    }

    /**
     * 更新病房信息
     * 
     * @param ward 待更新的病房信息
     * @return 返回影响的行数，1表示更新成功，0表示更新失败
     */
    public int updateWard(Ward ward) {
        ward.setUpdateTime(LocalDateTime.now());
        return wardMapper.updateWard(ward);
    }

    /**
     * 删除病房信息
     * 
     * @param wardId 待删除的病房ID
     * @return 返回影响的行数，1表示删除成功，0表示删除失败
     */
    public int deleteWard(String wardId) {
        return wardMapper.deleteWard(wardId);
    }
} 