package com.example.hospital.service;

import com.example.hospital.entity.Bed;
import com.example.hospital.mapper.BedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 床位服务层
 * 处理与床位相关的业务逻辑
 */
@Service
public class BedService {
    
    @Autowired
    private BedMapper bedMapper;

    /**
     * 获取所有床位信息
     */
    public List<Bed> getAllBeds() {
        return bedMapper.getAllBeds();
    }

    /**
     * 获取病房的所有床位
     */
    public List<Bed> getBedsByWardId(String wardId) {
        return bedMapper.getBedsByWardId(wardId);
    }

    /**
     * 获取病房的空闲床位
     */
    public List<Bed> getAvailableBedsByWardId(String wardId) {
        return bedMapper.getAvailableBedsByWardId(wardId);
    }

    /**
     * 根据床位ID查询床位信息
     */
    public Bed getBedById(String bedId) {
        return bedMapper.getBedById(bedId);
    }

    /**
     * 根据病房ID和床位号查询床位信息
     */
    public Bed getBedByWardIdAndNumber(String wardId, Integer bedNumber) {
        return bedMapper.getBedByWardIdAndNumber(wardId, bedNumber);
    }

    /**
     * 更新床位状态
     */
    public int updateBedStatus(Bed bed) {
        return bedMapper.updateBedStatus(bed);
    }
} 