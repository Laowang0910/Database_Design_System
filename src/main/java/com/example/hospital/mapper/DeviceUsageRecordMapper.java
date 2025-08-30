package com.example.hospital.mapper;

import com.example.hospital.entity.DeviceUsageRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeviceUsageRecordMapper {

    // 查询所有设备使用记录
    @Select("SELECT `device_id`, `service_time`, `use_annotations` FROM `设备使用记录表`")
    List<DeviceUsageRecord> getAllDeviceUsageRecords();

    // 插入新的设备使用记录
    @Insert("INSERT INTO `设备使用记录表` (`device_id`, `service_time`, `use_annotations`) VALUES (#{deviceId}, #{serviceTime}, #{useAnnotations})")
    int insertDeviceUsageRecord(DeviceUsageRecord record);
} 