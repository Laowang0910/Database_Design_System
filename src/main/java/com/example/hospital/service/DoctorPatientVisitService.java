package com.example.hospital.service;

import com.example.hospital.entity.DoctorUndiagnosedPatientView;
import com.example.hospital.entity.DoctorPatientVisit;
import com.example.hospital.mapper.DoctorPatientVisitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorPatientVisitService {

    @Autowired
    private DoctorPatientVisitMapper doctorPatientVisitMapper;

    // 获取所有未就诊患者视图
    public List<DoctorUndiagnosedPatientView> getAllUndiagnosedPatientViews() {
        return doctorPatientVisitMapper.getAllUndiagnosedPatientViews();
    }

    // 根据医生编号获取未就诊患者视图
    public List<DoctorUndiagnosedPatientView> getUndiagnosedPatientViewsByStaffId(String staffId) {
        return doctorPatientVisitMapper.getUndiagnosedPatientViewsByStaffId(staffId);
    }

    // 更新就诊状态为已就诊
    public boolean updateStateToDiagnosed(String staffId, String patientId) {
        int rowsAffected = doctorPatientVisitMapper.updateStateToDiagnosed(staffId, patientId);
        return rowsAffected > 0;
    }

    // 添加新的未就诊记录
    public boolean addUndiagnosedVisit(DoctorPatientVisit visit) {
        // 可以添加一些业务逻辑，例如检查患者和医生是否存在等
        int rowsAffected = doctorPatientVisitMapper.insertUndiagnosedVisit(visit);
        return rowsAffected > 0;
    }
} 