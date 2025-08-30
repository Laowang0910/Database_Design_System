package com.example.hospital.service;

import com.example.hospital.entity.MedicalWorker;
import com.example.hospital.mapper.MedicalWorkerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.time.LocalDateTime;

/**
 * 医护人员服务层
 * 处理与医护人员相关的业务逻辑
 */
@Service
public class MedicalWorkerService {
    
    @Autowired
    private MedicalWorkerMapper medicalWorkerMapper;

    @Autowired
    private UserService userService;

    /**
     * 获取所有医护人员信息
     * 
     * @return 返回医护人员信息列表
     */
    public List<MedicalWorker> getAllMedicalWorkers() {
        return medicalWorkerMapper.getAllMedicalWorkers();
    }

    /**
     * 根据员工ID查询医护人员信息
     * 
     * @param staffId 员工ID
     * @return 返回医护人员信息
     */
    public MedicalWorker getMedicalWorkerById(String staffId) {
        return medicalWorkerMapper.getMedicalWorkerById(staffId);
    }

    /**
     * 根据科室查询医护人员信息
     * 
     * @param department 科室名称
     * @return 返回该科室的医护人员列表
     */
    public List<MedicalWorker> getMedicalWorkersByDepartment(String department) {
        return medicalWorkerMapper.getMedicalWorkersByDepartment(department);
    }

    /**
     * 添加新医护人员
     * 
     * @param medicalWorker 待添加的医护人员信息
     * @return 返回影响的行数，1表示添加成功，0表示添加失败
     */
    public int addMedicalWorker(MedicalWorker medicalWorker) {
        medicalWorker.setCreateTime(LocalDateTime.now());
        medicalWorker.setUpdateTime(LocalDateTime.now());
        return medicalWorkerMapper.insertMedicalWorker(medicalWorker);
    }

    /**
     * 更新医护人员信息
     * 
     * @param medicalWorker 待更新的医护人员信息
     * @return 返回影响的行数，1表示更新成功，0表示更新失败
     */
    public int updateMedicalWorker(MedicalWorker medicalWorker) {
        medicalWorker.setUpdateTime(LocalDateTime.now());
        return medicalWorkerMapper.updateMedicalWorker(medicalWorker);
    }

    /**
     * 删除医护人员信息
     * 先删除用户账号表中的相关账号，再删除医护人员表中的记录
     * 
     * @param staffId 待删除的员工ID
     * @return 返回影响的行数，1表示删除成功，0表示删除失败
     */
    @Transactional
    public int deleteMedicalWorker(String staffId) {
        try {
            // 1. 先删除用户账号表中的相关账号
            int userResult = userService.deleteUser(staffId);
            if (userResult <= 0) {
                return 0; // 如果删除用户账号失败，直接返回
            }
            
            // 2. 再删除医护人员表中的记录
            int medicalWorkerResult = medicalWorkerMapper.deleteMedicalWorker(staffId);
            if (medicalWorkerResult <= 0) {
                throw new RuntimeException("删除医护人员记录失败"); // 如果删除医护人员记录失败，抛出异常以触发事务回滚
            }
            
            return 1; // 两个操作都成功
        } catch (Exception e) {
            throw new RuntimeException("删除医护人员失败: " + e.getMessage(), e);
        }
    }
} 