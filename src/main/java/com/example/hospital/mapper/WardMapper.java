package com.example.hospital.mapper;

import com.example.hospital.entity.Ward;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 病房数据访问层接口
 * 定义与数据库交互的方法
 */
@Mapper
public interface WardMapper {
    
    /**
     * 查询所有病房信息
     * 
     * @return 返回病房信息列表
     */
    @Select("SELECT * FROM `病房信息表`")
    List<Ward> getAllWards();

    /**
     * 根据病房ID查询病房信息
     * 
     * @param wardId 病房ID
     * @return 返回病房信息
     */
    @Select("SELECT * FROM `病房信息表` WHERE `ward_id` = #{wardId}")
    Ward getWardById(String wardId);

    /**
     * 插入新病房信息
     * 
     * @param ward 待插入的病房信息
     * @return 返回影响的行数
     */
    @Insert("INSERT INTO `病房信息表` (`ward_id`, `ward_name`, `ward_type`, `floor_number`, `total_beds`, " +
            "`available_beds`, `ward_status`, `create_time`, `updata_time`) " +
            "VALUES (#{wardId}, #{wardName}, #{wardType}, #{floorNumber}, #{totalBeds}, " +
            "#{availableBeds}, #{wardStatus}, #{createTime}, #{updateTime})")
    int insertWard(Ward ward);

    /**
     * 更新病房信息
     * 
     * @param ward 待更新的病房信息
     * @return 返回影响的行数
     */
    @Update("UPDATE `病房信息表` SET " +
            "`ward_name` = #{wardName}, " +
            "`ward_type` = #{wardType}, " +
            "`floor_number` = #{floorNumber}, " +
            "`total_beds` = #{totalBeds}, " +
            "`available_beds` = #{availableBeds}, " +
            "`ward_status` = #{wardStatus}, " +
            "`updata_time` = #{updateTime} " +
            "WHERE `ward_id` = #{wardId}")
    int updateWard(Ward ward);

    /**
     * 删除病房信息
     * 
     * @param wardId 待删除的病房ID
     * @return 返回影响的行数
     */
    @Delete("DELETE FROM `病房信息表` WHERE `ward_id` = #{wardId}")
    int deleteWard(String wardId);
} 