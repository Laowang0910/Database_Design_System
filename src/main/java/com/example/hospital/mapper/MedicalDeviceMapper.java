package com.example.hospital.mapper;

import com.example.hospital.entity.MedicalDevice;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 医疗设备数据访问层接口
 * 定义与数据库交互的方法
 */
@Mapper
public interface MedicalDeviceMapper {
    
    /**
     * 查询所有医疗设备信息
     * 
     * @return 返回医疗设备信息列表
     */
    @Select("SELECT * FROM 医疗设备表")
    List<MedicalDevice> getAllMedicalDevices();

    /**
     * 根据设备ID查询医疗设备信息
     * 
     * @param deviceId 设备ID
     * @return 返回医疗设备信息
     */
    @Select("SELECT * FROM 医疗设备表 WHERE device_id = #{deviceId}")
    MedicalDevice getMedicalDeviceById(String deviceId);

    /**
     * 根据设备类型查询医疗设备信息
     * 
     * @param deviceType 设备类型
     * @return 返回该类型的医疗设备列表
     */
    @Select("SELECT * FROM 医疗设备表 WHERE device_type = #{deviceType}")
    List<MedicalDevice> getMedicalDevicesByType(String deviceType);

    /**
     * 插入新医疗设备信息
     * 
     * @param medicalDevice 待插入的医疗设备信息
     * @return 返回影响的行数
     */
    @Insert("INSERT INTO 医疗设备表 (device_id, device_name, device_type, model_number, " +
            "manufacturer, purchase_date, warranty_date, ward_id, device_status, create_time, update_time) " +
            "VALUES (#{deviceId}, #{deviceName}, #{deviceType}, #{modelNumber}, " +
            "#{manufacturer}, #{purchaseDate}, #{warrantyDate}, #{wardId}, #{deviceStatus}, #{createTime}, #{updateTime})")
    int insertMedicalDevice(MedicalDevice medicalDevice);

    /**
     * 更新医疗设备信息
     * 
     * @param medicalDevice 待更新的医疗设备信息
     * @return 返回影响的行数
     */
    @Update("UPDATE 医疗设备表 SET " +
            "device_name = #{deviceName}, " +
            "device_type = #{deviceType}, " +
            "model_number = #{modelNumber}, " +
            "manufacturer = #{manufacturer}, " +
            "purchase_date = #{purchaseDate}, " +
            "warranty_date = #{warrantyDate}, " +
            "ward_id = #{wardId}, " +
            "device_status = #{deviceStatus}, " +
            "update_time = #{updateTime} " +
            "WHERE device_id = #{deviceId}")
    int updateMedicalDevice(MedicalDevice medicalDevice);

    /**
     * 删除医疗设备信息
     * 
     * @param deviceId 待删除的设备ID
     * @return 返回影响的行数
     */
    @Delete("DELETE FROM 医疗设备表 WHERE device_id = #{deviceId}")
    int deleteMedicalDevice(String deviceId);
} 