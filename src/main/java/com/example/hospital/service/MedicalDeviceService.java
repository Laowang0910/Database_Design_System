package com.example.hospital.service;

import com.example.hospital.entity.MedicalDevice;
import com.example.hospital.mapper.MedicalDeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;

/**
 * 医疗设备服务层
 * 处理与医疗设备相关的业务逻辑
 */
@Service
public class MedicalDeviceService {
    
    @Autowired
    private MedicalDeviceMapper medicalDeviceMapper;

    /**
     * 获取所有医疗设备信息
     * 
     * @return 返回医疗设备信息列表
     */
    public List<MedicalDevice> getAllMedicalDevices() {
        return medicalDeviceMapper.getAllMedicalDevices();
    }

    /**
     * 根据设备ID查询医疗设备信息
     * 
     * @param deviceId 设备ID
     * @return 返回医疗设备信息
     */
    public MedicalDevice getMedicalDeviceById(String deviceId) {
        return medicalDeviceMapper.getMedicalDeviceById(deviceId);
    }

    /**
     * 根据设备类型查询医疗设备信息
     * 
     * @param deviceType 设备类型
     * @return 返回该类型的医疗设备列表
     */
    public List<MedicalDevice> getMedicalDevicesByType(String deviceType) {
        return medicalDeviceMapper.getMedicalDevicesByType(deviceType);
    }

    /**
     * 添加新医疗设备
     * 
     * @param medicalDevice 待添加的医疗设备信息
     * @return 返回影响的行数，1表示添加成功，0表示添加失败
     */
    public int addMedicalDevice(MedicalDevice medicalDevice) {
        medicalDevice.setCreateTime(LocalDateTime.now());
        medicalDevice.setUpdateTime(LocalDateTime.now());
        // 如果设备状态为空，设置为"正常"
        if (medicalDevice.getDeviceStatus() == null || medicalDevice.getDeviceStatus().trim().isEmpty()) {
            medicalDevice.setDeviceStatus("正常");
        }
        return medicalDeviceMapper.insertMedicalDevice(medicalDevice);
    }

    /**
     * 更新医疗设备信息
     * 
     * @param medicalDevice 待更新的医疗设备信息
     * @return 返回影响的行数，1表示更新成功，0表示更新失败
     */
    public int updateMedicalDevice(MedicalDevice medicalDevice) {
        medicalDevice.setUpdateTime(LocalDateTime.now());
        return medicalDeviceMapper.updateMedicalDevice(medicalDevice);
    }

    /**
     * 删除医疗设备信息
     * 
     * @param deviceId 待删除的设备ID
     * @return 返回影响的行数，1表示删除成功，0表示删除失败
     */
    public int deleteMedicalDevice(String deviceId) {
        return medicalDeviceMapper.deleteMedicalDevice(deviceId);
    }
} 