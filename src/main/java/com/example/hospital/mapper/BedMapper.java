package com.example.hospital.mapper;

import com.example.hospital.entity.Bed;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 床位数据访问层接口
 */
@Mapper
public interface BedMapper {
    
    /**
     * 获取所有床位信息
     */
    @Select("SELECT * FROM 床位信息表")
    List<Bed> getAllBeds();

    /**
     * 获取病房的所有床位
     */
    @Select("SELECT bed_id, ward_id, bed_number, bed_status, bed_price, create_time, update_time " +
            "FROM 床位信息表 WHERE ward_id = #{wardId} ORDER BY bed_number")
    @Results({
        @Result(property = "bedId", column = "bed_id"),
        @Result(property = "wardId", column = "ward_id"),
        @Result(property = "bedNumber", column = "bed_number"),
        @Result(property = "bedStatus", column = "bed_status"),
        @Result(property = "bedPrice", column = "bed_price"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
    List<Bed> getBedsByWardId(String wardId);

    /**
     * 获取病房的空闲床位
     */
    @Select("SELECT bed_id, ward_id, bed_number, bed_status, bed_price, create_time, update_time " +
            "FROM 床位信息表 WHERE ward_id = #{wardId} AND bed_status = '空闲' ORDER BY bed_number")
    @Results({
        @Result(property = "bedId", column = "bed_id"),
        @Result(property = "wardId", column = "ward_id"),
        @Result(property = "bedNumber", column = "bed_number"),
        @Result(property = "bedStatus", column = "bed_status"),
        @Result(property = "bedPrice", column = "bed_price"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
    List<Bed> getAvailableBedsByWardId(String wardId);

    /**
     * 根据床位ID查询床位信息
     */
    @Select("SELECT * FROM 床位信息表 WHERE bed_id = #{bedId}")
    Bed getBedById(String bedId);

    /**
     * 根据病房ID和床位号查询床位信息
     */
    @Select("SELECT * FROM `床位信息表` WHERE `ward_id` = #{wardId} AND `bed_number` = #{bedNumber}")
    @Results({
        @Result(property = "bedId", column = "bed_id"),
        @Result(property = "wardId", column = "ward_id"),
        @Result(property = "bedNumber", column = "bed_number"),
        @Result(property = "bedStatus", column = "bed_status"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
    Bed getBedByWardIdAndNumber(@Param("wardId") String wardId, @Param("bedNumber") Integer bedNumber);

    /**
     * 更新床位状态
     */
    @Update("UPDATE `床位信息表` SET `bed_status` = #{bedStatus}, `update_time` = #{updateTime} " +
            "WHERE `bed_id` = #{bedId}")
    int updateBedStatus(Bed bed);
} 