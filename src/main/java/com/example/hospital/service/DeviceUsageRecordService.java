package com.example.hospital.service;

import com.example.hospital.entity.DeviceUsageRecord;
import com.example.hospital.mapper.DeviceUsageRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeviceUsageRecordService {

    @Autowired
    private DeviceUsageRecordMapper deviceUsageRecordMapper;

    // 获取所有设备使用记录
    public List<DeviceUsageRecord> getAllDeviceUsageRecords() {
        return deviceUsageRecordMapper.getAllDeviceUsageRecords();
    }

    // 添加新的设备使用记录
    public boolean addDeviceUsageRecord(DeviceUsageRecord record) {
        // 设置使用时间为当前时间
        record.setServiceTime(LocalDateTime.now());
        // 插入记录到数据库
        int rowsAffected = deviceUsageRecordMapper.insertDeviceUsageRecord(record);
        return rowsAffected > 0;
    }
} 