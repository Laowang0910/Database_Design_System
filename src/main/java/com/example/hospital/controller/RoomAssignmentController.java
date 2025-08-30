package com.example.hospital.controller;

import com.example.hospital.service.RoomAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/room-assignments")
public class RoomAssignmentController {

    @Autowired
    private RoomAssignmentService roomAssignmentService;

    @PostMapping
    public ResponseEntity<Void> assignRoom(@RequestBody Map<String, String> payload) {
        String patientId = payload.get("patientId");
        String wardId = payload.get("wardId");
        String bedId = payload.get("bedNumber"); // Frontend sends bedId in bedNumber field

        if (patientId == null || wardId == null || bedId == null) {
            return ResponseEntity.badRequest().build();
        }

        roomAssignmentService.assignRoom(patientId, wardId, bedId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/discharge")
    public ResponseEntity<Void> dischargePatient(@RequestBody Map<String, String> payload) {
        String patientId = payload.get("patientId");
        String bedId = payload.get("bedId");
        if (patientId == null || bedId == null) {
            return ResponseEntity.badRequest().build();
        }
        roomAssignmentService.dischargePatient(patientId, bedId);
        return ResponseEntity.ok().build();
    }
} 