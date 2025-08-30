package com.example.hospital.mapper;

import com.example.hospital.entity.Patient;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 患者数据访问层接口
 * 定义与数据库交互的方法
 * 
 * @Mapper 标注这是一个MyBatis的Mapper接口，由MyBatis框架实现数据库操作
 */
@Mapper
public interface PatientMapper {
    
    /**
     * 查询所有患者信息
     * 通过SQL语句从患者信息表中获取所有记录
     * 
     * @Select 注解中包含查询的SQL语句
     * @return 返回患者信息列表
     */
    @Select("SELECT * FROM `患者信息表`")
    @Results({
        @Result(property = "patientId", column = "patient_id"),
        @Result(property = "patientName", column = "patient_name"),
        @Result(property = "idNumber", column = "id_number"),
        @Result(property = "phoneNumber", column = "phone_number"),
        @Result(property = "emergencyContact", column = "emergency_contact"),
        @Result(property = "emergencyPhone", column = "emergency_phone"),
        @Result(property = "admissionTime", column = "admission_time"),
        @Result(property = "dischargeTime", column = "discharge_time"),
        @Result(property = "patientStatus", column = "patient_status"),
        @Result(property = "wardId", column = "ward_id"),
        @Result(property = "bedNumber", column = "bed_number"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time"),
        @Result(property = "callStatus", column = "call_status")
    })
    List<Patient> getAllPatients();

    /**
     * 根据ID查询患者信息
     */
    @Select("SELECT * FROM `患者信息表` WHERE `patient_id` = #{patientId}")
    @Results({
        @Result(property = "patientId", column = "patient_id"),
        @Result(property = "patientName", column = "patient_name"),
        @Result(property = "idNumber", column = "id_number"),
        @Result(property = "phoneNumber", column = "phone_number"),
        @Result(property = "emergencyContact", column = "emergency_contact"),
        @Result(property = "emergencyPhone", column = "emergency_phone"),
        @Result(property = "admissionTime", column = "admission_time"),
        @Result(property = "dischargeTime", column = "discharge_time"),
        @Result(property = "patientStatus", column = "patient_status"),
        @Result(property = "wardId", column = "ward_id"),
        @Result(property = "bedNumber", column = "bed_number"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time"),
        @Result(property = "callStatus", column = "call_status")
    })
    Patient getPatientById(String patientId);

    /**
     * 插入新患者信息
     * 通过SQL语句向患者信息表中插入一条新记录
     * 
     * @Insert 注解中包含插入的SQL语句
     * @param patient 待插入的患者信息
     * @return 返回影响的行数，1表示插入成功，0表示插入失败
     */
    @Insert("INSERT INTO `患者信息表` (`patient_id`, `patient_name`, `gender`, `age`, " +
            "`id_number`, `phone_number`, `address`, `emergency_contact`, `emergency_phone`, " +
            "`admission_time`, `discharge_time`, `patient_status`, `diagnosis`, " +
            "`ward_id`, `bed_number`, `create_time`, `update_time`, `call_status`) " +
            "VALUES (#{patientId}, #{patientName}, #{gender}, #{age}, " +
            "#{idNumber}, #{phoneNumber}, #{address}, #{emergencyContact}, #{emergencyPhone}, " +
            "#{admissionTime}, #{dischargeTime}, #{patientStatus}, #{diagnosis}, " +
            "#{wardId}, #{bedNumber}, #{createTime}, #{updateTime}, #{callStatus})")
    int insertPatient(Patient patient);

    /**
     * 更新患者信息
     * 通过SQL语句更新患者信息表中的记录
     * 
     * @Update 注解中包含更新的SQL语句
     * @param patient 待更新的患者信息
     * @return 返回影响的行数，1表示更新成功，0表示更新失败
     */
    @Update("UPDATE `患者信息表` SET " +
            "`patient_name` = #{patientName}, " +
            "`gender` = #{gender}, " +
            "`age` = #{age}, " +
            "`id_number` = #{idNumber}, " +
            "`phone_number` = #{phoneNumber}, " +
            "`address` = #{address}, " +
            "`emergency_contact` = #{emergencyContact}, " +
            "`emergency_phone` = #{emergencyPhone}, " +
            "`admission_time` = #{admissionTime}, " +
            "`discharge_time` = #{dischargeTime}, " +
            "`patient_status` = #{patientStatus}, " +
            "`diagnosis` = #{diagnosis}, " +
            "`ward_id` = #{wardId}, " +
            "`bed_number` = #{bedNumber}, " +
            "`update_time` = #{updateTime}, " +
            "`call_status` = #{callStatus} " +
            "WHERE `patient_id` = #{patientId}")
    int updatePatient(Patient patient);

    /**
     * 删除患者信息
     * 通过SQL语句删除患者信息表中的记录
     * 
     * @Delete 注解中包含删除的SQL语句
     * @param patientId 待删除的患者ID
     * @return 返回影响的行数，1表示删除成功，0表示删除失败
     */
    @Delete("DELETE FROM `患者信息表` WHERE `patient_id` = #{patientId}")
    int deletePatient(String patientId);

    /**
     * 根据病房和床位号查询住院患者
     */
    @Select("SELECT * FROM `患者信息表` WHERE `ward_id` = #{wardId} AND `bed_number` = #{bedNumber} AND `patient_status` = 'ADMITTED'")
    Patient getPatientByBed(@Param("wardId") String wardId, @Param("bedNumber") Integer bedNumber);

    @Update("UPDATE `患者信息表` SET `call_status` = '未呼叫' WHERE `patient_id` = #{patientId}")
    int updateCallStatusToProcessed(@Param("patientId") String patientId);

    @Update("UPDATE `患者信息表` SET `call_status` = '呼叫' WHERE `patient_id` = #{patientId}")
    int updateCallStatusToRequest(@Param("patientId") String patientId);
} 