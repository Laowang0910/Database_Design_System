package com.example.hospital.controller;

import com.example.hospital.entity.Bed;
import com.example.hospital.service.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 床位控制器
 */
@RestController
@RequestMapping("/api/beds")
public class BedController {
    
    @Autowired
    private BedService bedService;

    /**
     * 获取所有床位信息
     */
    @GetMapping
    public ResponseEntity<List<Bed>> getAllBeds() {
        List<Bed> beds = bedService.getAllBeds();
        return ResponseEntity.ok(beds);
    }

    /**
     * 获取病房的所有床位
     */
    @GetMapping("/ward/{wardId}")
    public ResponseEntity<List<Bed>> getBedsByWardId(@PathVariable String wardId) {
        List<Bed> beds = bedService.getBedsByWardId(wardId);
        return ResponseEntity.ok(beds);
    }

    /**
     * 获取病房的空闲床位
     */
    @GetMapping("/ward/{wardId}/available")
    public ResponseEntity<List<Bed>> getAvailableBedsByWardId(@PathVariable String wardId) {
        List<Bed> beds = bedService.getAvailableBedsByWardId(wardId);
        return ResponseEntity.ok(beds);
    }

    /**
     * 根据床位ID查询床位信息
     */
    @GetMapping("/{bedId}")
    public ResponseEntity<?> getBedById(@PathVariable String bedId) {
        Bed bed = bedService.getBedById(bedId);
        if (bed != null) {
            return ResponseEntity.ok(bed);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 根据病房ID和床位号查询床位信息
     */
    @GetMapping("/ward/{wardId}/number/{bedNumber}")
    public ResponseEntity<?> getBedByWardIdAndNumber(
            @PathVariable String wardId,
            @PathVariable Integer bedNumber) {
        Bed bed = bedService.getBedByWardIdAndNumber(wardId, bedNumber);
        if (bed != null) {
            return ResponseEntity.ok(bed);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 更新床位状态
     */
    @PutMapping("/{bedId}/status")
    public ResponseEntity<?> updateBedStatus(
            @PathVariable String bedId,
            @RequestBody Map<String, String> request) {
        Bed bed = bedService.getBedById(bedId);
        if (bed == null) {
            return ResponseEntity.notFound().build();
        }

        String newStatus = request.get("status");
        if (newStatus == null || newStatus.trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "床位状态不能为空");
            return ResponseEntity.badRequest().body(error);
        }

        bed.setBedStatus(newStatus);
        int result = bedService.updateBedStatus(bed);
        
        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("success", true);
            response.put("message", "床位状态更新成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "床位状态更新失败");
            return ResponseEntity.badRequest().body(response);
        }
    }
} 