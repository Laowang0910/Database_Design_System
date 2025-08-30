package com.example.hospital.service;

import com.example.hospital.entity.Patient;
import com.example.hospital.entity.Bed;
import com.example.hospital.mapper.PatientMapper;
import com.example.hospital.mapper.BedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.time.LocalDateTime;

/**
 * 患者服务层
 * 处理与患者相关的业务逻辑
 * 
 * @Service 标注这是一个服务层组件，由Spring容器管理
 */
@Service
public class PatientService {
    
    /**
     * 注入患者数据访问层组件
     * 通过@Autowired实现依赖注入，由Spring自动装配PatientMapper实例
     */
    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private BedMapper bedMapper;

    /**
     * 获取所有患者信息
     * 调用数据访问层获取患者列表
     * 
     * @return 返回所有患者信息列表
     */
    public List<Patient> getAllPatients() {
        return patientMapper.getAllPatients();
    }

    /**
     * 添加新患者
     * 调用数据访问层插入患者信息
     * 
     * @param patient 待添加的患者信息
     * @return 返回影响的行数，1表示添加成功，0表示添加失败
     */
    public int addPatient(Patient patient) {
        patient.setCreateTime(LocalDateTime.now());
        patient.setUpdateTime(LocalDateTime.now());
        return patientMapper.insertPatient(patient);
    }

    /**
     * 更新患者信息
     * 调用数据访问层更新患者信息
     * 
     * @param patient 待更新的患者信息
     * @return 返回影响的行数，1表示更新成功，0表示更新失败
     */
    public int updatePatient(Patient patient) {
        patient.setUpdateTime(LocalDateTime.now());
        return patientMapper.updatePatient(patient);
    }

    /**
     * 删除患者信息
     * 调用数据访问层删除患者信息
     * 
     * @param patientId 待删除的患者ID
     * @return 返回影响的行数，1表示删除成功，0表示删除失败
     */
    public int deletePatient(String patientId) {
        return patientMapper.deletePatient(patientId);
    }

    /**
     * 根据ID获取患者信息
     * 
     * @param patientId 患者ID
     * @return 返回患者信息
     */
    public Patient getPatientById(String patientId) {
        return patientMapper.getPatientById(patientId);
    }

    /**
     * 为患者分配床位
     */
    @Transactional
    public int assignBed(String patientId, String wardId, Integer bedNumber) {
        // 检查床位是否存在且可用
        Bed bed = bedMapper.getBedByWardIdAndNumber(wardId, bedNumber);
        if (bed == null || !"AVAILABLE".equals(bed.getBedStatus())) {
            return 0;
        }

        // 检查床位是否已被其他患者占用
        Patient existingPatient = patientMapper.getPatientByBed(wardId, bedNumber);
        if (existingPatient != null) {
            return 0;
        }

        // 获取患者信息
        Patient patient = getPatientById(patientId);
        if (patient == null) {
            return 0;
        }

        // 更新患者信息
        patient.setWardId(wardId);
        patient.setBedNumber(bedNumber);
        patient.setPatientStatus("ADMITTED");
        patient.setAdmissionTime(LocalDateTime.now());
        patient.setUpdateTime(LocalDateTime.now());
        
        // 更新床位状态
        bed.setBedStatus("OCCUPIED");
        bed.setUpdateTime(LocalDateTime.now());
        bedMapper.updateBedStatus(bed);

        return updatePatient(patient);
    }
} 