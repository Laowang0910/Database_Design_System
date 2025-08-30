package com.example.hospital.mapper;

import com.example.hospital.entity.MedicalWorker;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 医护人员数据访问层接口
 * 定义与数据库交互的方法
 */
@Mapper
public interface MedicalWorkerMapper {
    
    /**
     * 查询所有医务人员信息
     * 
     * @return 返回医务人员信息列表
     */
    @Select("SELECT * FROM `医护人员表`")
    @Results({
        @Result(property = "staffId", column = "staff_id"),
        @Result(property = "staffName", column = "staff_name"),
        @Result(property = "phoneNumber", column = "phone_number"),
        @Result(property = "entryTime", column = "entry_time"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
    List<MedicalWorker> getAllMedicalWorkers();

    /**
     * 根据ID查询医务人员信息
     * 
     * @param staffId 员工ID
     * @return 返回医务人员信息
     */
    @Select("SELECT * FROM `医护人员表` WHERE `staff_id` = #{staffId}")
    @Results({
        @Result(property = "staffId", column = "staff_id"),
        @Result(property = "staffName", column = "staff_name"),
        @Result(property = "phoneNumber", column = "phone_number"),
        @Result(property = "entryTime", column = "entry_time"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
    MedicalWorker getMedicalWorkerById(String staffId);

    /**
     * 根据科室查询医务人员信息
     * 
     * @param department 科室名称
     * @return 返回该科室的医务人员列表
     */
    @Select("SELECT * FROM `医护人员表` WHERE department = #{department}")
    List<MedicalWorker> getMedicalWorkersByDepartment(String department);

    /**
     * 插入新医务人员信息
     * 
     * @param medicalWorker 待插入的医务人员信息
     * @return 返回影响的行数
     */
    @Insert("INSERT INTO `医护人员表` (`staff_id`, `staff_name`, `gender`, `age`, `phone_number`, `department`, `position`, `title`, `create_time`, `update_time`) VALUES (#{staffId}, #{staffName}, #{gender}, #{age}, #{phoneNumber}, #{department}, #{position}, #{title}, #{createTime}, #{updateTime})")
    int insertMedicalWorker(MedicalWorker medicalWorker);

    /**
     * 更新医务人员信息
     * 
     * @param medicalWorker 待更新的医务人员信息
     * @return 返回影响的行数
     */
    @Update("UPDATE `医护人员表` SET `staff_name` = #{staffName}, `gender` = #{gender}, `age` = #{age}, `phone_number` = #{phoneNumber}, `department` = #{department}, `position` = #{position}, `title` = #{title}, `create_time` = #{createTime}, `update_time` = #{updateTime} WHERE `staff_id` = #{staffId}")
    int updateMedicalWorker(MedicalWorker medicalWorker);

    /**
     * 删除医务人员信息
     * 
     * @param staffId 待删除的员工ID
     * @return 返回影响的行数
     */
    @Delete("DELETE FROM `医护人员表` WHERE `staff_id` = #{staffId}")
    int deleteMedicalWorker(String staffId);
} 