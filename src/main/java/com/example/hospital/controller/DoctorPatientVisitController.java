package com.example.hospital.controller;

import com.example.hospital.entity.DoctorUndiagnosedPatientView;
import com.example.hospital.entity.DoctorPatientVisit;
import com.example.hospital.service.DoctorPatientVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor-patient-visits")
@CrossOrigin
public class DoctorPatientVisitController {

    @Autowired
    private DoctorPatientVisitService doctorPatientVisitService;

    // GET 请求：获取所有医生未就诊患者视图数据
    @GetMapping
    public ResponseEntity<List<DoctorUndiagnosedPatientView>> getAllUndiagnosedPatientViews() {
        List<DoctorUndiagnosedPatientView> views = doctorPatientVisitService.getAllUndiagnosedPatientViews();
        return ResponseEntity.ok(views);
    }

    // GET 请求：根据医生编号获取医生未就诊患者视图数据
    @GetMapping("/doctor/{staffId}")
    public ResponseEntity<List<DoctorUndiagnosedPatientView>> getUndiagnosedPatientViewsByStaffId(@PathVariable String staffId) {
        List<DoctorUndiagnosedPatientView> views = doctorPatientVisitService.getUndiagnosedPatientViewsByStaffId(staffId);
        if (views.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(views);
        }
    }

    // PUT 请求：修改医患就诊表中的 state 状态为已就诊
    @PutMapping("/{staffId}/{patientId}")
    public ResponseEntity<Void> updateStateToDiagnosed(
            @PathVariable String staffId,
            @PathVariable String patientId) {
        boolean success = doctorPatientVisitService.updateStateToDiagnosed(staffId, patientId);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    // POST 请求：向医患就诊表中加入患者编号和医生编号，状态为未就诊
    @PostMapping
    public ResponseEntity<Void> addUndiagnosedVisit(@RequestBody DoctorPatientVisit visit) {
        // 在 Service 中已经设置了状态为未就诊，这里只需要接收 staffId 和 patientId
        boolean success = doctorPatientVisitService.addUndiagnosedVisit(visit);
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
} 