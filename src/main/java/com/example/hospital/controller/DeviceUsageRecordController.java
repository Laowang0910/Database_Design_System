package com.example.hospital.controller;

import com.example.hospital.entity.DeviceUsageRecord;
import com.example.hospital.service.DeviceUsageRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/device-usage-records")
@CrossOrigin
public class DeviceUsageRecordController {

    @Autowired
    private DeviceUsageRecordService deviceUsageRecordService;

    // GET 请求：获取所有设备使用记录
    @GetMapping
    public ResponseEntity<List<DeviceUsageRecord>> getAllDeviceUsageRecords() {
        List<DeviceUsageRecord> records = deviceUsageRecordService.getAllDeviceUsageRecords();
        return ResponseEntity.ok(records);
    }

    // POST 请求：添加新的设备使用记录
    @PostMapping
    public ResponseEntity<Void> addDeviceUsageRecord(@RequestBody DeviceUsageRecord record) {
        // 前端只需提供 deviceId 和 useAnnotations，serviceTime 会在 Service 层设置
        boolean success = deviceUsageRecordService.addDeviceUsageRecord(record);
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
} 